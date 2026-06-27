# java-translation

基于 Spring Boot + llama-server 的轻量翻译服务。

## 功能

- 调用本地 LLM（llama-server）实现文本翻译
- 支持指定源语言和目标语言
- 默认支持中英文互译
- OpenAI 兼容的 Chat API 调用方式
- 无数据库依赖，开箱即用

## API

### 翻译

```http
POST /api/translate
Content-Type: application/x-www-form-urlencoded

text=你好&sourceLanguage=中文&targetLanguage=英文
```

| 参数 | 说明 |
|------|------|
| text | 待翻译文本 |
| sourceLanguage | 源语言 |
| targetLanguage | 目标语言 |

## 配置

| 配置项 | 说明 |
|--------|------|
| `llama.server-url` | llama-server 地址，默认 `http://host.docker.internal:9000` |

## 本地运行

```bash
mvn spring-boot:run -Dllama.server-url=http://localhost:9000
```

## Docker 部署

```bash
docker-compose up -d
```

服务默认运行在 **7006** 端口。
