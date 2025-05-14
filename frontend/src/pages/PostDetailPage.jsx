import React, { useEffect, useState } from 'react';
import { useParams, Link, useNavigate } from 'react-router-dom';
import { getPostById, downloadFile, deletePost } from '../api/postApi';

function PostDetailPage() {
    const { id } = useParams();
    const [post, setPost] = useState(null);
    const [currentUserEmail, setCurrentUserEmail] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        getPostById(id)
            .then(res => {
                setPost(res.data);
            })
            .catch(err => {
                console.error(err);
                alert('게시글 불러오기 실패!');
            });

        const token = localStorage.getItem('token');
        if (token) {
            try {
                const payload = JSON.parse(atob(token.split('.')[1]));
                setCurrentUserEmail(payload.sub);
            } catch (e) {
                console.error('토큰 파싱 실패:', e);
            }
        }
    }, [id]);

    const handleDownload = (fileId) => {
        downloadFile(fileId);
    };

    const handleDelete = async () => {
        const confirmDelete = window.confirm('정말 삭제하시겠습니까?');
        if (!confirmDelete) return;

        try {
            await deletePost(post.id);
            alert('삭제 완료!');
            navigate('/posts');
        } catch (err) {
            console.error(err);
            alert('삭제 실패!');
        }
    };

    if (!post) return <div>로딩 중...</div>;

    return (
        <div>
            <h2>{post.title}</h2>
            <p><strong>작성자:</strong> {post.author}</p>
            <p><strong>작성일:</strong> {new Date(post.createdAt).toLocaleString()}</p>
            <p>{post.content}</p>

            {post.fileNames.length > 0 && (
                <div>
                    <h4>📎 첨부파일</h4>
                    <ul>
                        {post.fileNames.map((fileName, index) => (
                            <li key={index}>
                                <button onClick={() => handleDownload(post.fileIds[index])}>
                                    {fileName}
                                </button>
                            </li>
                        ))}
                    </ul>
                </div>
            )}

            {post.author === currentUserEmail && (
                <div>
                    <Link to={`/posts/${post.id}/edit`}>✏️ 수정하기</Link>
                    &nbsp;|&nbsp;
                    <button onClick={handleDelete}>🗑 삭제하기</button>
                </div>
            )}
        </div>
    );
}

export default PostDetailPage;
