import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { getPostById, updatePost } from '../api/postApi';

function PostEditPage() {
    const { id } = useParams();
    const navigate = useNavigate();
    const [form, setForm] = useState({
        title: '',
        content: '',
    });

    useEffect(() => {
        getPostById(id)
            .then(res => {
                setForm({
                    title: res.data.title,
                    content: res.data.content,
                });
            })
            .catch(err => {
                console.error(err);
                alert('게시글 불러오기 실패!');
            });
    }, [id]);

    const handleChange = (e) => {
        setForm({
            ...form,
            [e.target.name]: e.target.value,
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            await updatePost(id, form);
            alert('게시글 수정 완료!');
            navigate(`/posts/${id}`);
        } catch (err) {
            console.error(err);
            alert('수정 실패!');
        }
    };

    return (
        <div>
            <h2>게시글 수정</h2>
            <form onSubmit={handleSubmit}>
                <input
                    type="text"
                    name="title"
                    placeholder="제목"
                    value={form.title}
                    onChange={handleChange}
                    required
                />
                <br />
                <textarea
                    name="content"
                    placeholder="내용"
                    value={form.content}
                    onChange={handleChange}
                    required
                />
                <br />
                <button type="submit">수정하기</button>
            </form>
        </div>
    );
}

export default PostEditPage;
