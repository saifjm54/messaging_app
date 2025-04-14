# 📬 Real-Time Messaging Service

A scalable real-time messaging platform built with **Spring Boot**, **Redis**, **WebSocket**, and **React**. This service enables authenticated users to send and receive private messages in real time.

---

## 🚀 Features

- 🔒 Secure WebSocket connection with JWT authentication
- 💬 Private one-to-one messaging
- 🔄 Real-time updates using STOMP over WebSocket
- 📡 Scalable Redis Pub/Sub backend
- 🧪 Built with TDD: unit & integration tested
- 🧑‍💻 Modern React frontend

---

## 🏗️ System Design

### 🔹 High-Level Architecture

React Client ─────── WebSocket ───────▶ Spring Boot (WS Controller) │ │ │ ▼ ◀────────── Message to User ◀── Redis Pub/Sub


### 🔹 Low-Level Components

- `/app/send` — Send message (STOMP)
- `/user/{username}/queue/messages` — Receive private messages
- Redis Topic: `chat`

---

## 🧑‍💻 Tech Stack

| Layer       | Tech                                  |
|-------------|---------------------------------------|
| Frontend    | React, TypeScript, STOMP.js, SockJS   |
| Backend     | Spring Boot, Spring WebSocket, STOMP  |
| Messaging   | Redis Pub/Sub                         |
| Authentication | JWT, Spring Security              |
| Testing     | JUnit, Jest, React Testing Library    |
| Deployment  | Docker, CI/CD                         |

---

## ⚙️ Getting Started

### 🐳 Backend (Spring Boot)

```bash ./mvnw spring-boot:run 

# Or build and run with Docker

docker build -t messaging-service .
docker run -p 8080:8080 messaging-service

# Frontend (React)

cd frontend
npm install
npm start




