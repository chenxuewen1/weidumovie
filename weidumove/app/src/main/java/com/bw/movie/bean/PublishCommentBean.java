package com.bw.movie.bean;

/*Time:2019/4/17
 *Author:chenxuewen
 *Description:发表评论
 */
public class PublishCommentBean {

    /**
     * message : 评论成功
     * status : 0000
     */

    private String message;
    private String status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
