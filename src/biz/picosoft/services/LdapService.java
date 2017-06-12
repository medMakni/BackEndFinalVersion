package biz.picosoft.services;

import java.util.List;
import java.util.Map;

public interface LdapService  {
List<String> getAllDirection();
String findUserDirectionAndRoleById(String uid) ;
List<String> getSousGroup(String direction);
}
