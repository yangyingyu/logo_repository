package cn.itsource.logo.util;

public class AjaxResult {
    private boolean success=true;  //表示操作成功
    private String msg="操作成功";  //返回一个文本字符串信息
    private Object object;  //有时候不单单 可能返回一个字符串，也有可能返回一个对象

    /*
    * 提供一个获取对象实例的方法
    * */
    public static AjaxResult me(){
        return  new AjaxResult();
    }
   /* public AjaxResult() {
    }

    public AjaxResult(boolean success, String msg, Object object) {
        this.success = success;
        this.msg = msg;
        this.object = object;
    }*/

    public boolean isSuccess() {
        return success;
    }
    //返回一个ajax对象
    public AjaxResult setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public String getMsg() {
        return msg;
    }
//返回一个Ajax实例对象
    public AjaxResult setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Object getObject() {
        return object;
    }
    //返回一个Ajax实例对象
    public AjaxResult setObject(Object object) {
        this.object = object;
        return this;
    }

    @Override
    public String toString() {
        return "AjaxResult{" +
                "success=" + success +
                ", msg='" + msg + '\'' +
                ", object=" + object +
                '}';
    }
}
