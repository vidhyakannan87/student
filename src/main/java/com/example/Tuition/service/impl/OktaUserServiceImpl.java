package com.example.Tuition.service.impl;

import com.example.Tuition.api.request.StudentRequest;
import com.example.Tuition.service.OktaUserService;
import com.okta.sdk.client.Client;
import com.okta.sdk.resource.user.User;
import com.okta.sdk.resource.user.UserBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OktaUserServiceImpl implements OktaUserService {

  private final Client client;

  @Value("${okta.usersGroupId}")
  private String groupId;

  public OktaUserServiceImpl(Client client) {
    this.client = client;
  }

  @Override
  public String createUser(StudentRequest studentRequest) {
    char[] passwordChar = studentRequest.getPassword().toCharArray();

    User oktaUser = UserBuilder.instance()
            .setEmail(studentRequest.getEmail())
            .setFirstName(studentRequest.getFirstName())
            .setLastName(studentRequest.getLastName())
            .setPassword(passwordChar)
            .addGroup(groupId)
            .setActive(true)
            .buildAndCreate(client);

    return oktaUser.getId();
  }
}
