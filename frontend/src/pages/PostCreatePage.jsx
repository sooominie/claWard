import React, { useState } from 'react';
import { createPost, uploadFile } from '../api/postApi';
import { useNavigate } from 'react-router-dom';

function PostCreatePage() {
    const [form, setForm] = useState({ title: '', content: '' });
    const [file, setFile] = useState(null);
    const navigate = useNavigate();

    const handleChange = (e) => {
        setForm({ ...form, [e.target.name]: e.target.value });
    };

    const handleFileChange = (e) => {
        setFile(e.target.files[0]);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            const res = await createPost(form); // 게시글 먼저 등록
            const postId = res.data.id;

            if (file) {
                await uploadFile(postId, file); // 파일 업로드
            }

            alert('게시글 작성 완료!');
            navigate('/');
        } catch (err) {
            console.error(err);
            alert('게시글 작성 실패!');
        }
    };

    return (
        <div>
            <h2>게시글 작성</h2>
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
                <input type="file" onChange={handleFileChange} />
                <br />
                <button type="submit">작성</button>
            </form>
        </div>
    );
}

export default PostCreatePage;
