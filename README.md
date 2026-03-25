# Java Data Governance Dashboard / Java 数据治理看板

## CN 简介
课程/训练型全栈项目（非生产系统），用于演示 `Spring Boot + MyBatis-Plus + Vue + ECharts` 的完整开发流程。
## 项目效果
<img width="1600" height="1200" alt="dashboard" src="https://github.com/user-attachments/assets/8f74c958-8ce0-4802-bb08-f95043fc3ece" />


## EN Summary
A course-level full-stack prototype (not production) demonstrating end-to-end development with Spring Boot, MyBatis-Plus, Vue, and ECharts.

## CN 功能与数据
- 核心功能：记录管理（增删改查 + 条件筛选）
- 统计图表：3 张（数据源占比、平均偏差、月度趋势）
- 后端接口：7 个
- 默认样例数据：5 条（`backend/src/main/resources/data.sql`）
- 数据表：`data_record`（8 列，含 id）

## EN Features & Metrics
- Core features: record CRUD + filtering
- Charts: 3 (source ratio, average deviation, monthly trend)
- Backend APIs: 7
- Seed data: 5 rows (`backend/src/main/resources/data.sql`)
- Table: `data_record` (8 columns including id)

## Tech Stack
- Backend: Spring Boot 3.3, MyBatis-Plus, H2
- Frontend: Vue 3, Axios, ECharts, Vite

## Quick Start
### Backend
```bash
cd backend
mvn spring-boot:run
```
URL: `http://localhost:8081`

### Frontend
```bash
cd frontend
npm install
npm run dev
```
URL: `http://localhost:5173`

## API List
- `GET /api/records`
- `POST /api/records`
- `PUT /api/records/{id}`
- `DELETE /api/records/{id}`
- `GET /api/stats/source`
- `GET /api/stats/deviation`
- `GET /api/stats/month-trend`

## Low-Risk Statement / 降风险说明
- 本项目为课程训练原型，数据为本地样例与手动录入数据。
- 未接入鉴权、分布式部署、生产级监控。
