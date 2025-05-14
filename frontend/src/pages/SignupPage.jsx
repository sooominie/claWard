import React, { useState } from 'react';
import { signup, login } from '../api/memberApi';
import { useNavigate } from 'react-router-dom';

function SignupPage() {
    const [form, setForm] = useState({
        email: '',
        password: ''
    });

    const navigate = useNavigate();

    const handleChange = (e) => {
        setForm({
            ...form,
            [e.target.name]: e.target.value,
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await signup(form); // 회원가입
            const res = await login(form); // 바로 로그인
            localStorage.setItem('token', res.data); // 토큰 저장
            alert('회원가입 및 로그인 성공!');
            window.dispatchEvent(new Event('storage')); // 로그인 후 App에 알려줌

            navigate('/posts'); // 게시글 목록 이동
        } catch (err) {
            console.error(err);
            alert('회원가입 실패!');
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <h2>회원가입</h2>
            <input
                type="email"
                name="email"
                placeholder="이메일"
                value={form.email}
                onChange={handleChange}
                required
            />
            <br />
            <input
                type="password"
                name="password"
                placeholder="비밀번호"
                value={form.password}
                onChange={handleChange}
                required
            />
            <br />
            <button type="submit">회원가입</button>
        </form>
    );
}

export default SignupPage;
