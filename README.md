# plugin-umami

Halo 2.0 对 [Umami](https://github.com/umami-software/umami) 的集成。

![Umami](./screenshots//umami.png)

## 特性

- 支持注入 Umami 的统计代码到每个页面的 head 标签内。
- 支持设置分享链接以供在 Halo 控制台查看统计数据。

## 后续计划

- [ ] 通过调用 Umami 的接口，实现一些 Dashboard 的 Widget。

## 安装与使用

1. 在 [Releases](https://github.com/halo-sigs/plugin-umami/releases) 下载最新的 JAR 文件。
2. 在 Halo 后台的插件管理上传 JAR 文件进行安装。
3. 启动该插件之后，需要在设置配置 Umami 的相关信息
4. 进入插件设置页面，配置以下信息：

   - Umami 的站点地址：Umami 服务的外部访问地址
   - 脚本名称：根据实际情况添加，Umami 1.x 默认为 umami.js，Umami 2.x 默认为 script.js
   - 站点 ID：可在 Umami 的设置 -> 网站 -> 编辑中找到
   - 共享链接：共享链接需要您手动为对应的网站开启，设置之后可以在 Halo 后台的 Umami 菜单进行访问。

## FAQ

1. 在 Console 无法加载 Umami 的页面，出现了 CORS 和 CSP 的错误。

    1. 方式 1：在 Umami 反代的配置文件中加入如下配置(请替换域名)：
       
        ```
        server{
            listen 443 ssl http2;
            server_name [umami.domain.com];
            #...
            add_header Access-Control-Allow-Origin 'https://halo.domain.com';
            add_header Access-Control-Allow-Methods 'GET, POST, OPTIONS';
            add_header Access-Control-Allow-Headers 'DNT,X-Mx-ReqToken,Keep-Alive,User-Agent,X-Requested-With,If-Modified-Since,Cache-Control,Content-Type,Authorization';
            if ($request_method = 'OPTIONS') {
                return 204;
            }
        
            add_header Content-Security-Policy 'frame-ancestors halo.domain.com';
            location / {
                #...
                proxy_hide_header 'Access-Control-Allow-Origin';
                proxy_hide_header 'Content-Security-Policy';
            }
        
        }
        ```
    3. 方式 2：为 Umami 的容器添加环境变量 `ALLOWED_FRAME_URLS`，值为 Halo 的访问地址，例：
  
        ```
        ALLOWED_FRAME_URLS=https://www.halo.run
        ```
        > 此方式适用于 Umami 2.3.0 以上版本。
