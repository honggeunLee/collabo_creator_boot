package org.example.collabo_creator_boot.security.auth;

import java.security.Principal;

public class CreatorLoginPrincipal implements Principal {

    private final String adminId;

    public CreatorLoginPrincipal(final String adminId) {
        this.adminId = adminId;
    }

    @Override
    public String getName() {
        return adminId;
    }

}

