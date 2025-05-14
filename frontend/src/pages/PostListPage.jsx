import React, { useEffect, useState } from 'react';
import { getAllPosts } from '../api/postApi';
import { Link } from 'react-router-dom';

function PostListPage() {
    const [posts, setPosts] = useState([]);

    useEffect(() => {
        getAllPosts()
            .then(res => setPosts(res.data))
            .catch(err => {
                console.error(err);
                alert('게시글 목록 불러오기 실패!');
            });
    }, []);

    return (
        <div>
            <h2>📄 게시글 목록</h2>
            <ul>
                {posts.map(post => (
                    <li key={post.id}>
                        <Link to={`/posts/${post.id}`}>
                            <strong>{post.title}</strong> — {post.author} / {new Date(post.createdAt).toLocaleString()}
                            {post.hasFile && <span> 📎</span>}
                        </Link>
                    </li>
                ))}
            </ul>
            <br />
            <Link to="/posts/new">➕ 새 글 작성</Link>
        </div>
    );
}

export default PostListPage;
