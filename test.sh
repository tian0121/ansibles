rg="$1"

# 脚本帮助信息
usage(){
  echo "脚本 $0 的使用方式是: $0 [ start|stop|restart ]"
}

# 函数主框架
if [ $# -eq 1 ]
then
  case "${arg}" in
    start)
      echo "启动中..."
    ;;
    stop)
      echo "关闭中..."
    ;;
    restart)
      echo "重启中..."
    ;;
    *)
      usage
    ;;
  esac
else
  usage
fi
