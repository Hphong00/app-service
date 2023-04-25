package com.app.productservice.core.constant;
public class AuthorityConstant {

    public static final String ROLE_BANK_ADMIN = "ADMIN-SEAPARTNER";
    public static final String ROLE_MANAGEMENT_BRANCH = "AGENT-MANAGER";
    public static final String ROLE_KSV = "AGENT-KSV";
    public static final String ROLE_GDV = "AGENT-GDV";


    public static final String TRANSACTION_MANAGEMENTS_R = "R-/transaction-management/search";
    public static final String TRANSACTION_MANAGEMENTS_I = "I-/transaction-management/search";
    public static final String TRANSACTION_MANAGEMENTS_D_I = "I-/transaction-management/detail-management";
    public static final String TRANSACTION_MANAGEMENTS_D_R = "R-/transaction-management/detail-management";
    public static final String TRANSACTION_MANAGEMENTS_C_I = "I-/transaction-management/check-transaction";
    public static final String TRANSACTION_MANAGEMENTS_C_R = "R-/transaction-management/check-transaction";
    public static final String TRANSACTION_MANAGEMENTS_H_I = "I-/transaction-management/history-transaction";
    public static final String TRANSACTION_MANAGEMENTS_H_R = "R-/transaction-management/history-transaction";

    //PARNERT
    public static final String PARNERT_VIEWS = "R-/agent-management";
    public static final String PARNERT_ADD = "I-/agent-management/add";
    public static final String PARNERT_EDIT = "I-/agent-management/edit";
    public static final String PARNERT_DELETE = "I-/agent-management";
    //PARNERT_BRANCH
    public static final String PARNERT_BRANCH_VIEWS = "R-/branch-agent";
    public static final String PARNERT_BRANCH_ADD = "I-/branch-agent/add";
    public static final String PARNERT_BRANCH_EDIT = "I-/branch-agent/add";
    public static final String PARNERT_BRANCH_DELETE = "I-/branch-agent";
    //sea_partner_link
    public static final String SEA_PARTNER_LINK_VIEWS = "R-/link-management";
    public static final String SEA_PARTNER_LINK_ADD = "I-/link-management/add";
    public static final String SEA_PARTNER_LINK_EDIT = "I-/link-management/edit";
    public static final String SEA_PARTNER_LINK_IMPORT = "I-/link-management/import";
    public static final String SEA_PARTNER_LINK_EXPORT = "R-/link-management/import";
    //Branch
    public static final String BRANCH = "R-/agent";
    public static final String DETAIL_BRANCH = "I-/agent/detail";
    public static final String SYNC_BRANCH = "I-/agent/sync";

    //Cut Off Time
    public static final String CUT_OFF_TIME_I = "I-/cut-off-time";
    public static final String CUT_OFF_TIME_R = "R-/cut-off-time";
}
