// src/App.js
import HomePage from './pages/HomePage';
import React, { useEffect, useState } from 'react';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import Header from './components/Header';
import SignupPage from './pages/SignupPage';
import LoginPage from './pages/LoginPage';
import PostCreatePage from './pages/PostCreatePage';
import PostListPage from './pages/PostListPage';
import PostDetailPage from './pages/PostDetailPage';
import MyPostListPage from './pages/MyPostListPage';
import PostEditPage from './pages/PostEditPage';

function App() {
    const [token, setToken] = useState(localStorage.getItem('token'));

    // localStorage 변경 시 token 상태 반영
    useEffect(() => {
        const syncToken = () => {
            setToken(localStorage.getItem('token'));
        };
        window.addEventListener('storage', syncToken);
        return () => window.removeEventListener('storage', syncToken);
    }, []);

    return (
        <BrowserRouter>
            <Header />
            <Routes>
                <Route path="/" element={<HomePage />} />
                <Route path="/" element={
                    token ? <Navigate to="/posts" /> : <Navigate to="/login" />
                } />
                <Route path="/signup" element={<SignupPage />} />
                <Route path="/login" element={<LoginPage />} />
                <Route path="/posts" element={token ? <PostListPage /> : <Navigate to="/login" />} />
                <Route path="/posts/new" element={token ? <PostCreatePage /> : <Navigate to="/login" />} />
                <Route path="/posts/:id" element={token ? <PostDetailPage /> : <Navigate to="/login" />} />
                <Route path="/posts/:id/edit" element={token ? <PostEditPage /> : <Navigate to="/login" />} />
                <Route path="/myposts" element={token ? <MyPostListPage /> : <Navigate to="/login" />} />
            </Routes>
        </BrowserRouter>
    );
}

export default App;
