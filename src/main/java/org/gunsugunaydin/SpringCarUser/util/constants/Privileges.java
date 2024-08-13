package org.gunsugunaydin.SpringCarUser.util.constants;

public enum Privileges {
    
    ACCESS_USER_ENDPOINTS(1L, "ACCESS_USER_ENDPOINTS"),
    ACCESS_ADMIN_ENDPOINTS(2L, "ACCESS_ADMIN_ENDPOINTS");

    private final Long id;
    private final String privilege;
    
    private Privileges(Long id, String privilege) {
        this.id = id;
        this.privilege = privilege;
    }
    
    public Long getId() {
        return id;
    }
    
    public String getPrivilege() {
        return privilege;
    }    
}
