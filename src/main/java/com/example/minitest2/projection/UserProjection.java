package com.example.minitest2.projection;

import com.example.minitest2.entity.User;
import lombok.RequiredArgsConstructor;

public interface UserProjection {
    Long getId();
    String getName();
    String getEmail();

    @RequiredArgsConstructor
    class UserProjectionImpl implements UserProjection{
        private final User user;

        @Override
        public Long getId() {
            return this.user.getId();
        }

        @Override
        public String getName() {
            return this.user.getName();
        }

        @Override
        public String getEmail() {
            return this.user.getEmail();
        }
    }
    static UserProjection of(User user){
        return new UserProjectionImpl(user);
    }
}
