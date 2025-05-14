import React, { useState } from 'react';
import { login } from '../api/memberApi';
import { useNavigate } from 'react-router-dom';

function LoginPage() {
    const [form, setForm] = useState({ email: '', password: '' });
    const navigate = useNavigate();

    const handleChange = (e) => {
        setForm({ ...form, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const res = await login(form); //  로그인 요청 후 응답 받기
            localStorage.setItem('token', res.data); //  토큰 저장
            alert('로그인 성공!');
            window.dispatchEvent(new Event('storage')); // 로그인 후 App에 알려줌

            navigate('/posts'); //  바로 이동
        } catch (err) {
            console.error(err);
            alert('로그인 실패!');
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <h2>로그인</h2>
            <input
                type="email"
                name="email"
                value={form.email}
                onChange={handleChange}
                placeholder="이메일"
                required
            />
            <input
                type="password"
                name="password"
                value={form.password}
                onChange={handleChange}
                placeholder="비밀번호"
                required
            />
            <button type="submit">로그인</button>
        </form>
    );
}

export default LoginPage;
