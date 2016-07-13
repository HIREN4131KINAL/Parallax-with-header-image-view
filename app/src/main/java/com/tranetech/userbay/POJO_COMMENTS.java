package com.tranetech.userbay;

/**
 * Created by HIREN AMALIYAR on 13/07/2016.
 */
public class POJO_COMMENTS {
    String comments;
    String cr_iv_profile;

    public String getComments() {
        return comments;
    }

    public String getCr_iv_profile() {
        return cr_iv_profile;
    }

    public POJO_COMMENTS(String comments, String cr_iv_profile) {
        super();
        this.comments = comments;
        this.cr_iv_profile = cr_iv_profile;
    }
}
