## Installation

### 安装第三方软件：

包括但不限于PHP、Composer、Git、MySQL

### 克隆代码

从服务器上下载源代码，可以直接下载zip包，当然最好是通过git的本地客户端管理源代码

### 下载依赖的第三方库

在根目录运行“php composer.phar install”，或者“composer install”（全局安装了composer的情况下）

### Laravel框架配置

1）复制根目录下的.env.example并重命名为.env，在这里可以配置数据库的信息(以“DB_”开头)，其他配置暂时没有什么用

2）设置APP_KEY：在根目录下运行“php artisan key:generate”

### PHP配置

1）依赖库（php_mysql.dll/php_mysqli.dll/php_pdo_mysql.dll/其他）：直接在php.ini中打开对应的库（删除行首的“；”）或者使用包管理器安装（类linux系统）

### 连接数据库

修改配置为：162.105.30.65:9011 root 123456（公共数据库）

也可以连接本地安装的数据库

如果是本地安装的数据库，需要运行"php artisan migrate:refresh --seed" 来初始化数据库表并且导入初始数据库

### 运行测试服务器

1）在根目录下运行“php artisan serve”

### 快捷方式

1）Windows操作系统下可以使用"run_server.bat"或"run_server_light.bat"，前者在配置好.env之后会自动完成整个服务器的配置和启动，后者仅启动服务器