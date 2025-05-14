import React, { useEffect, useState } from 'react';
import { getMyPosts } from '../api/postApi';
import { Link } from 'react-router-dom';

function MyPostListPage() {
    const [posts, setPosts] = useState([]);

    useEffect(() => {
        getMyPosts()
            .then(res => setPosts(res.data))
            .catch(err => {
                console.error(err);
                alert('내 게시글 목록 불러오기 실패!');
            });
    }, []);

    return (
        <div>
            <h2>🧾 내가 쓴 글</h2>
            {posts.length === 0 ? (
                <p>작성한 글이 없습니다.</p>
            ) : (
                <ul>
                    {posts.map(post => (
                        <li key={post.id}>
                            <Link to={`/posts/${post.id}`}>
                                <strong>{post.title}</strong> — {new Date(post.createdAt).toLocaleString()}
                                {post.hasFile && <span> 📎</span>}
                            </Link>
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
}

export default MyPostListPage;
