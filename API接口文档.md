# 酒店管理系统 API 接口文档

## 基础信息

- **服务地址**: http://localhost:8088
- **Token名称**: satoken
- **Token前缀**: xt
- **数据格式**: JSON
- **字符编码**: UTF-8

---

## 通用响应格式

所有接口均返回以下格式：

```json
{
  "code": 200,
  "message": "success",
  "data": {}
}
```

**字段说明：**
- `code`: 状态码（200-成功，其他-失败）
- `message`: 响应消息
- `data`: 响应数据

---

## 一、认证模块 (/auth)

### 1.1 用户注册

**接口地址**: POST `/auth/register`

**请求头**:
```
Content-Type: application/json
```

**请求体**:
```json
{
  "username": "testuser",
  "nickname": "测试用户",
  "password": "123456",
  "email": "test@example.com",
  "phone": "13800138000",
  "identity": "user"
}
```

**必填字段**: username, password, identity

**响应示例**:
```json
{
  "code": 200,
  "message": "注册成功",
  "data": null
}
```

---

### 1.2 用户登录

**接口地址**: POST `/auth/login`

**请求头**:
```
Content-Type: application/json
```

**请求体**:
```json
{
  "username": "testuser",
  "password": "123456",
  "identity": "user"
}
```

**必填字段**: username, password, identity

**响应示例**:
```json
{
  "code": 200,
  "message": "登录成功",
  "data": {
    "userId": "1",
    "username": "testuser",
    "token": "xt_xxxxxxxxxxxx"
  }
}
```

**注意**: 登录成功后，后续请求需要在Header中携带token：
```
satoken: xt_xxxxxxxxxxxx
```

---

### 1.3 用户注销

**接口地址**: GET `/auth/logout`

**请求头**:
```
satoken: xt_xxxxxxxxxxxx
```

**响应示例**:
```json
{
  "code": 200,
  "message": "测试注销",
  "data": null
}
```

---

## 二、用户管理模块 (/user)

### 2.1 查询所有用户列表

**接口地址**: GET `/user/test`

**请求头**:
```
satoken: xt_xxxxxxxxxxxx
```

**响应示例**:
```json
{
  "code": 200,
  "message": "查询完成",
  "data": [
    {
      "id": 1,
      "username": "testuser",
      "nickname": "测试用户",
      "email": "test@example.com",
      "phone": "13800138000",
      "identity": "user",
      "createTime": "2026-04-16 10:00:00",
      "updateTime": "2026-04-16 10:00:00",
      "deleted": 0
    }
  ]
}
```

---

### 2.2 分页查询用户

**接口地址**: GET `/user/page?current=1&size=10`

**请求参数**:
- `current`: 当前页码（默认1）
- `size`: 每页大小（默认10）

**请求头**:
```
satoken: xt_xxxxxxxxxxxx
```

**响应示例**:
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "records": [],
    "total": 0,
    "size": 10,
    "current": 1,
    "pages": 0
  }
}
```

---

### 2.3 模糊查询用户

**接口地址**: GET `/user/userPage?current=1&size=10&username=test`

**请求参数**:
- `current`: 当前页码（默认1）
- `size`: 每页大小（默认10）
- `username`: 用户名关键词（支持模糊查询）

**请求头**:
```
satoken: xt_xxxxxxxxxxxx
```

**响应示例**:
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "records": [],
    "total": 0,
    "size": 10,
    "current": 1,
    "pages": 0
  }
}
```

---

### 2.4 根据ID获取用户信息

**接口地址**: GET `/user/{id}`

**路径参数**:
- `id`: 用户ID

**请求头**:
```
satoken: xt_xxxxxxxxxxxx
```

**响应示例**:
```json
{
  "code": 200,
  "message": "查询成功",
  "data": {
    "id": 1,
    "username": "testuser",
    "nickname": "测试用户",
    "email": "test@example.com",
    "phone": "13800138000",
    "identity": "user",
    "createTime": "2026-04-16 10:00:00",
    "updateTime": "2026-04-16 10:00:00",
    "deleted": 0
  }
}
```

---

### 2.5 修改用户信息

**接口地址**: POST `/user/edit?id=1`

**请求参数**:
- `id`: 用户ID（Query参数）

**请求头**:
```
Content-Type: application/json
satoken: xt_xxxxxxxxxxxx
```

**请求体**:
```json
{
  "username": "newusername",
  "nickname": "新昵称",
  "email": "newemail@example.com",
  "phone": "13900139000"
}
```

**响应示例**:
```json
{
  "code": 200,
  "message": "用户修改成功",
  "data": null
}
```

---

### 2.6 修改用户密码

**接口地址**: POST `/user/userPasswordUpdate?id=1&password=newpassword123`

**请求参数**:
- `id`: 用户ID
- `password`: 新密码

**请求头**:
```
satoken: xt_xxxxxxxxxxxx
```

**响应示例**:
```json
{
  "code": 200,
  "message": "用户密码修改成功",
  "data": null
}
```

---

### 2.7 逻辑删除用户

**接口地址**: DELETE `/user/delete?userid=1`

**请求参数**:
- `userid`: 用户ID

**请求头**:
```
satoken: xt_xxxxxxxxxxxx
```

**响应示例**:
```json
{
  "code": 200,
  "message": "用户删除成功",
  "data": null
}
```

---

### 2.8 业务删除用户

**接口地址**: DELETE `/user/editDeleted?userid=1`

**请求参数**:
- `userid`: 用户ID

**请求头**:
```
satoken: xt_xxxxxxxxxxxx
```

**响应示例**:
```json
{
  "code": 200,
  "message": "用户删除成功",
  "data": null
}
```

---

## 三、房间管理模块 (/room)

### 3.1 添加房间

**接口地址**: POST `/room/add`

**请求头**:
```
Content-Type: application/json
satoken: xt_xxxxxxxxxxxx
```

**请求体**:
```json
{
  "roomNumber": "101",
  "roomType": "标准间",
  "price": 299.00,
  "capacity": 2,
  "description": "舒适标准间，配备双人床",
  "images": "https://example.com/room1.jpg,https://example.com/room2.jpg",
  "facilities": "WiFi,空调,电视,独立卫生间",
  "status": 1,
  "merchantId": 1,
  "address": "北京市朝阳区xxx路xxx号"
}
```

**必填字段**: roomNumber, roomType, price, capacity

**响应示例**:
```json
{
  "code": 200,
  "message": "房间添加成功",
  "data": null
}
```

---

### 3.2 更新房间信息

**接口地址**: POST `/room/update`

**请求头**:
```
Content-Type: application/json
satoken: xt_xxxxxxxxxxxx
```

**请求体**:
```json
{
  "id": 1,
  "roomNumber": "101",
  "roomType": "豪华标准间",
  "price": 399.00,
  "capacity": 2,
  "description": "升级豪华标准间",
  "images": "https://example.com/room1_new.jpg",
  "facilities": "WiFi,空调,电视,独立卫生间,迷你吧",
  "status": 1,
  "merchantId": 1,
  "address": "北京市朝阳区xxx路xxx号"
}
```

**必填字段**: id（必须包含房间ID）

**响应示例**:
```json
{
  "code": 200,
  "message": "房间更新成功",
  "data": null
}
```

---

### 3.3 删除房间

**接口地址**: DELETE `/room/delete/{id}`

**路径参数**:
- `id`: 房间ID

**请求头**:
```
satoken: xt_xxxxxxxxxxxx
```

**响应示例**:
```json
{
  "code": 200,
  "message": "房间删除成功",
  "data": null
}
```

---

### 3.4 根据ID获取房间信息

**接口地址**: GET `/room/{id}`

**路径参数**:
- `id`: 房间ID

**请求头**:
```
satoken: xt_xxxxxxxxxxxx
```

**响应示例**:
```json
{
  "code": 200,
  "message": "查询成功",
  "data": {
    "id": 1,
    "roomNumber": "101",
    "roomType": "标准间",
    "price": 299.00,
    "capacity": 2,
    "description": "舒适标准间",
    "images": "https://example.com/room1.jpg",
    "facilities": "WiFi,空调,电视",
    "status": 1,
    "merchantId": 1,
    "address": "北京市朝阳区xxx路xxx号",
    "deleted": 0,
    "createTime": "2026-04-16T10:00:00",
    "updateTime": "2026-04-16T10:00:00"
  }
}
```

---

### 3.5 分页查询房间

**接口地址**: POST `/room/page`

**请求头**:
```
Content-Type: application/json
satoken: xt_xxxxxxxxxxxx
```

**请求体**:
```json
{
  "current": 1,
  "size": 10,
  "roomNumber": "101",
  "roomType": "标准间",
  "minPrice": 200.00,
  "maxPrice": 500.00,
  "capacity": 2,
  "status": 1,
  "merchantId": 1,
  "address": "北京"
}
```

**可选筛选条件**:
- `roomNumber`: 房间号（模糊查询）
- `roomType`: 房间类型（精确匹配）
- `minPrice`: 最低价格
- `maxPrice`: 最高价格
- `capacity`: 最少容纳人数
- `status`: 房间状态（0-不可用，1-可用，2-已预订，3-维修中）
- `merchantId`: 商家ID
- `address`: 地址（模糊查询）

**响应示例**:
```json
{
  "code": 200,
  "message": "查询成功",
  "data": {
    "records": [],
    "total": 0,
    "size": 10,
    "current": 1,
    "pages": 0
  }
}
```

---

### 3.6 根据商家ID获取房间列表

**接口地址**: GET `/room/merchant/{merchantId}`

**路径参数**:
- `merchantId`: 商家ID

**请求头**:
```
satoken: xt_xxxxxxxxxxxx
```

**响应示例**:
```json
{
  "code": 200,
  "message": "查询成功",
  "data": [
    {
      "id": 1,
      "roomNumber": "101",
      "roomType": "标准间",
      "price": 299.00,
      "capacity": 2,
      "status": 1,
      "merchantId": 1
    }
  ]
}
```

---

### 3.7 根据状态获取房间列表

**接口地址**: GET `/room/status/{status}`

**路径参数**:
- `status`: 房间状态（0-不可用，1-可用，2-已预订，3-维修中）

**请求头**:
```
satoken: xt_xxxxxxxxxxxx
```

**响应示例**:
```json
{
  "code": 200,
  "message": "查询成功",
  "data": [
    {
      "id": 1,
      "roomNumber": "101",
      "roomType": "标准间",
      "price": 299.00,
      "capacity": 2,
      "status": 1
    }
  ]
}
```

---

### 3.8 更新房间状态

**接口地址**: POST `/room/status/update?id=1&status=2`

**请求参数**:
- `id`: 房间ID
- `status`: 新状态（0-不可用，1-可用，2-已预订，3-维修中）

**请求头**:
```
satoken: xt_xxxxxxxxxxxx
```

**响应示例**:
```json
{
  "code": 200,
  "message": "状态更新成功",
  "data": null
}
```

---

### 3.9 多条件搜索房间

**接口地址**: POST `/room/search`

**请求头**:
```
Content-Type: application/json
satoken: xt_xxxxxxxxxxxx
```

**请求体**:
```json
{
  "roomNumber": "101",
  "roomType": "标准间",
  "minPrice": 200.00,
  "maxPrice": 500.00,
  "capacity": 2,
  "status": 1,
  "merchantId": 1,
  "address": "北京"
}
```

**说明**: 所有条件均为可选，支持组合查询

**响应示例**:
```json
{
  "code": 200,
  "message": "查询成功",
  "data": [
    {
      "id": 1,
      "roomNumber": "101",
      "roomType": "标准间",
      "price": 299.00,
      "capacity": 2,
      "status": 1
    }
  ]
}
```

---

### 3.10 获取所有房间列表

**接口地址**: GET `/room/list`

**请求头**:
```
satoken: xt_xxxxxxxxxxxx
```

**响应示例**:
```json
{
  "code": 200,
  "message": "查询成功",
  "data": [
    {
      "id": 1,
      "roomNumber": "101",
      "roomType": "标准间",
      "price": 299.00,
      "capacity": 2,
      "status": 1
    }
  ]
}
```

**注意**: 最多返回1000条记录

---

## Postman 测试集合导入格式

可以将以下内容保存为 `酒店管理系统API.postman_collection.json` 文件，然后导入到 Postman：

```json
{
  "info": {
    "name": "酒店管理系统API",
    "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
  },
  "item": [
    {
      "name": "认证模块",
      "item": [
        {
          "name": "用户注册",
          "request": {
            "method": "POST",
            "header": [
              {"key": "Content-Type", "value": "application/json"}
            ],
            "body": {
              "mode": "raw",
              "raw": "{\n  \"username\": \"testuser\",\n  \"nickname\": \"测试用户\",\n  \"password\": \"123456\",\n  \"email\": \"test@example.com\",\n  \"phone\": \"13800138000\",\n  \"identity\": \"user\"\n}"
            },
            "url": {
              "raw": "http://localhost:8088/auth/register",
              "protocol": "http",
              "host": ["localhost"],
              "port": "8088",
              "path": ["auth", "register"]
            }
          }
        },
        {
          "name": "用户登录",
          "request": {
            "method": "POST",
            "header": [
              {"key": "Content-Type", "value": "application/json"}
            ],
            "body": {
              "mode": "raw",
              "raw": "{\n  \"username\": \"testuser\",\n  \"password\": \"123456\",\n  \"identity\": \"user\"\n}"
            },
            "url": {
              "raw": "http://localhost:8088/auth/login",
              "protocol": "http",
              "host": ["localhost"],
              "port": "8088",
              "path": ["auth", "login"]
            }
          }
        },
        {
          "name": "用户注销",
          "request": {
            "method": "GET",
            "header": [
              {"key": "satoken", "value": "xt_token_here"}
            ],
            "url": {
              "raw": "http://localhost:8088/auth/logout",
              "protocol": "http",
              "host": ["localhost"],
              "port": "8088",
              "path": ["auth", "logout"]
            }
          }
        }
      ]
    },
    {
      "name": "用户管理",
      "item": [
        {
          "name": "查询所有用户",
          "request": {
            "method": "GET",
            "header": [
              {"key": "satoken", "value": "xt_token_here"}
            ],
            "url": {
              "raw": "http://localhost:8088/user/test",
              "protocol": "http",
              "host": ["localhost"],
              "port": "8088",
              "path": ["user", "test"]
            }
          }
        },
        {
          "name": "分页查询用户",
          "request": {
            "method": "GET",
            "header": [
              {"key": "satoken", "value": "xt_token_here"}
            ],
            "url": {
              "raw": "http://localhost:8088/user/page?current=1&size=10",
              "protocol": "http",
              "host": ["localhost"],
              "port": "8088",
              "path": ["user", "page"],
              "query": [
                {"key": "current", "value": "1"},
                {"key": "size", "value": "10"}
              ]
            }
          }
        },
        {
          "name": "模糊查询用户",
          "request": {
            "method": "GET",
            "header": [
              {"key": "satoken", "value": "xt_token_here"}
            ],
            "url": {
              "raw": "http://localhost:8088/user/userPage?current=1&size=10&username=test",
              "protocol": "http",
              "host": ["localhost"],
              "port": "8088",
              "path": ["user", "userPage"],
              "query": [
                {"key": "current", "value": "1"},
                {"key": "size", "value": "10"},
                {"key": "username", "value": "test"}
              ]
            }
          }
        },
        {
          "name": "根据ID获取用户",
          "request": {
            "method": "GET",
            "header": [
              {"key": "satoken", "value": "xt_token_here"}
            ],
            "url": {
              "raw": "http://localhost:8088/user/1",
              "protocol": "http",
              "host": ["localhost"],
              "port": "8088",
              "path": ["user", "1"]
            }
          }
        },
        {
          "name": "修改用户信息",
          "request": {
            "method": "POST",
            "header": [
              {"key": "Content-Type", "value": "application/json"},
              {"key": "satoken", "value": "xt_token_here"}
            ],
            "body": {
              "mode": "raw",
              "raw": "{\n  \"username\": \"newusername\",\n  \"nickname\": \"新昵称\",\n  \"email\": \"newemail@example.com\",\n  \"phone\": \"13900139000\"\n}"
            },
            "url": {
              "raw": "http://localhost:8088/user/edit?id=1",
              "protocol": "http",
              "host": ["localhost"],
              "port": "8088",
              "path": ["user", "edit"],
              "query": [
                {"key": "id", "value": "1"}
              ]
            }
          }
        },
        {
          "name": "修改用户密码",
          "request": {
            "method": "POST",
            "header": [
              {"key": "satoken", "value": "xt_token_here"}
            ],
            "url": {
              "raw": "http://localhost:8088/user/userPasswordUpdate?id=1&password=newpassword123",
              "protocol": "http",
              "host": ["localhost"],
              "port": "8088",
              "path": ["user", "userPasswordUpdate"],
              "query": [
                {"key": "id", "value": "1"},
                {"key": "password", "value": "newpassword123"}
              ]
            }
          }
        },
        {
          "name": "逻辑删除用户",
          "request": {
            "method": "DELETE",
            "header": [
              {"key": "satoken", "value": "xt_token_here"}
            ],
            "url": {
              "raw": "http://localhost:8088/user/delete?userid=1",
              "protocol": "http",
              "host": ["localhost"],
              "port": "8088",
              "path": ["user", "delete"],
              "query": [
                {"key": "userid", "value": "1"}
              ]
            }
          }
        },
        {
          "name": "业务删除用户",
          "request": {
            "method": "DELETE",
            "header": [
              {"key": "satoken", "value": "xt_token_here"}
            ],
            "url": {
              "raw": "http://localhost:8088/user/editDeleted?userid=1",
              "protocol": "http",
              "host": ["localhost"],
              "port": "8088",
              "path": ["user", "editDeleted"],
              "query": [
                {"key": "userid", "value": "1"}
              ]
            }
          }
        }
      ]
    },
    {
      "name": "房间管理",
      "item": [
        {
          "name": "添加房间",
          "request": {
            "method": "POST",
            "header": [
              {"key": "Content-Type", "value": "application/json"},
              {"key": "satoken", "value": "xt_token_here"}
            ],
            "body": {
              "mode": "raw",
              "raw": "{\n  \"roomNumber\": \"101\",\n  \"roomType\": \"标准间\",\n  \"price\": 299.00,\n  \"capacity\": 2,\n  \"description\": \"舒适标准间，配备双人床\",\n  \"images\": \"https://example.com/room1.jpg\",\n  \"facilities\": \"WiFi,空调,电视,独立卫生间\",\n  \"status\": 1,\n  \"merchantId\": 1,\n  \"address\": \"北京市朝阳区xxx路xxx号\"\n}"
            },
            "url": {
              "raw": "http://localhost:8088/room/add",
              "protocol": "http",
              "host": ["localhost"],
              "port": "8088",
              "path": ["room", "add"]
            }
          }
        },
        {
          "name": "更新房间信息",
          "request": {
            "method": "POST",
            "header": [
              {"key": "Content-Type", "value": "application/json"},
              {"key": "satoken", "value": "xt_token_here"}
            ],
            "body": {
              "mode": "raw",
              "raw": "{\n  \"id\": 1,\n  \"roomNumber\": \"101\",\n  \"roomType\": \"豪华标准间\",\n  \"price\": 399.00,\n  \"capacity\": 2,\n  \"description\": \"升级豪华标准间\",\n  \"status\": 1\n}"
            },
            "url": {
              "raw": "http://localhost:8088/room/update",
              "protocol": "http",
              "host": ["localhost"],
              "port": "8088",
              "path": ["room", "update"]
            }
          }
        },
        {
          "name": "删除房间",
          "request": {
            "method": "DELETE",
            "header": [
              {"key": "satoken", "value": "xt_token_here"}
            ],
            "url": {
              "raw": "http://localhost:8088/room/delete/1",
              "protocol": "http",
              "host": ["localhost"],
              "port": "8088",
              "path": ["room", "delete", "1"]
            }
          }
        },
        {
          "name": "根据ID获取房间",
          "request": {
            "method": "GET",
            "header": [
              {"key": "satoken", "value": "xt_token_here"}
            ],
            "url": {
              "raw": "http://localhost:8088/room/1",
              "protocol": "http",
              "host": ["localhost"],
              "port": "8088",
              "path": ["room", "1"]
            }
          }
        },
        {
          "name": "分页查询房间",
          "request": {
            "method": "POST",
            "header": [
              {"key": "Content-Type", "value": "application/json"},
              {"key": "satoken", "value": "xt_token_here"}
            ],
            "body": {
              "mode": "raw",
              "raw": "{\n  \"current\": 1,\n  \"size\": 10,\n  \"roomType\": \"标准间\",\n  \"minPrice\": 200.00,\n  \"maxPrice\": 500.00,\n  \"status\": 1\n}"
            },
            "url": {
              "raw": "http://localhost:8088/room/page",
              "protocol": "http",
              "host": ["localhost"],
              "port": "8088",
              "path": ["room", "page"]
            }
          }
        },
        {
          "name": "根据商家ID获取房间",
          "request": {
            "method": "GET",
            "header": [
              {"key": "satoken", "value": "xt_token_here"}
            ],
            "url": {
              "raw": "http://localhost:8088/room/merchant/1",
              "protocol": "http",
              "host": ["localhost"],
              "port": "8088",
              "path": ["room", "merchant", "1"]
            }
          }
        },
        {
          "name": "根据状态获取房间",
          "request": {
            "method": "GET",
            "header": [
              {"key": "satoken", "value": "xt_token_here"}
            ],
            "url": {
              "raw": "http://localhost:8088/room/status/1",
              "protocol": "http",
              "host": ["localhost"],
              "port": "8088",
              "path": ["room", "status", "1"]
            }
          }
        },
        {
          "name": "更新房间状态",
          "request": {
            "method": "POST",
            "header": [
              {"key": "satoken", "value": "xt_token_here"}
            ],
            "url": {
              "raw": "http://localhost:8088/room/status/update?id=1&status=2",
              "protocol": "http",
              "host": ["localhost"],
              "port": "8088",
              "path": ["room", "status", "update"],
              "query": [
                {"key": "id", "value": "1"},
                {"key": "status", "value": "2"}
              ]
            }
          }
        },
        {
          "name": "多条件搜索房间",
          "request": {
            "method": "POST",
            "header": [
              {"key": "Content-Type", "value": "application/json"},
              {"key": "satoken", "value": "xt_token_here"}
            ],
            "body": {
              "mode": "raw",
              "raw": "{\n  \"roomNumber\": \"101\",\n  \"roomType\": \"标准间\",\n  \"minPrice\": 200.00,\n  \"maxPrice\": 500.00,\n  \"capacity\": 2,\n  \"status\": 1\n}"
            },
            "url": {
              "raw": "http://localhost:8088/room/search",
              "protocol": "http",
              "host": ["localhost"],
              "port": "8088",
              "path": ["room", "search"]
            }
          }
        },
        {
          "name": "获取所有房间列表",
          "request": {
            "method": "GET",
            "header": [
              {"key": "satoken", "value": "xt_token_here"}
            ],
            "url": {
              "raw": "http://localhost:8088/room/list",
              "protocol": "http",
              "host": ["localhost"],
              "port": "8088",
              "path": ["room", "list"]
            }
          }
        }
      ]
    }
  ]
}
```

**导入步骤：**
1. 复制上面的JSON内容
2. 保存为 `酒店管理系统API.postman_collection.json` 文件
3. 打开 Postman，点击 Import
4. 选择该文件导入
5. 导入后需要将 `xt_token_here` 替换为实际的token值

---

## 常见错误码

| 错误码 | 说明 |
|--------|------|
| 200 | 成功 |
| 500 | 服务器内部错误 |
| 401 | 未授权（token无效或过期） |

---

## 注意事项

1. 所有需要认证的接口都需要在请求头中携带 `satoken`
2. Token格式为：`xt_xxxxxxxxxxxx`
3. 密码在数据库中以MD5加密存储
4. 删除操作均为逻辑删除，不会真正从数据库删除数据
5. 房间状态说明：0-不可用，1-可用，2-已预订，3-维修中
6. 时间格式：`yyyy-MM-dd HH:mm:ss`
