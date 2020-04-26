package com.example.websocketdemo.dto;

import lombok.Data;

import javax.security.auth.Subject;
import java.security.Principal;

/**
 * @author gaochao
 * @create 2020-04-26 10:17
 */
@Data
public class User implements Principal {

  private String userName;


  @Override
  public String getName() {
    return userName;
  }

  @Override
  public boolean implies(Subject subject) {
    return false;
  }
}
