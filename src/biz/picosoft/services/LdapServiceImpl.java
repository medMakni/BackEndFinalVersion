package biz.picosoft.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.identity.Group;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.DefaultDirObjectFactory;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.query.LdapQueryBuilder;
import org.springframework.ldap.query.SearchScope;

public class LdapServiceImpl implements LdapService {

	@Override
	public List<String> getAllDirection() {
		List<String> groupList = null;
		LdapQuery query = LdapQueryBuilder.query().base("ou=groups,o=mojo").searchScope(SearchScope.SUBTREE)
				.timeLimit(200).countLimit(221).where("objectclass").is("groupOfUniqueNames");
		LdapContextSource lcs = new LdapContextSource();

		lcs.setUrl("ldap://localhost:8389");
		lcs.setUserDn("uid=admin,ou=system");
		lcs.setPassword("secret");
		lcs.setDirObjectFactory(DefaultDirObjectFactory.class);
		lcs.afterPropertiesSet();
		LdapTemplate ldapTemplate = new LdapTemplate(lcs);
		// Attribute attr = attributes.get("cn");
		CourriersArrivésImpl courriersArrivésImpl = new CourriersArrivésImpl();

		groupList = ldapTemplate.list("cn=Direction Générale,ou=groups,o=mojo");
		List groupListeWithoutRole = new ArrayList<String>();
		for (int i = 0; i < groupList.size(); i++) {
			groupList.set(i, groupList.get(i).substring(groupList.get(i).indexOf("=") + 1, groupList.get(i).length()));

			if (groupList.get(i).contains("Direction"))
				groupListeWithoutRole.add(groupList.get(i));

		}
		groupListeWithoutRole.add("Direction Générale");
		return groupListeWithoutRole;

	}

	@Override
	public String findUserDirectionAndRoleById(String uid) {
		@SuppressWarnings("resource")
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("activit.cfg.xml");
		ProcessEngine processEngine = (ProcessEngine) applicationContext.getBean("processEngine");
		List<String> direction = new ArrayList<String>();
		List<String> role = new ArrayList<String>();
		Map<String, Object> map = new HashMap<String, Object>();
		List<Group> j = processEngine.getIdentityService().createGroupQuery().groupMember(uid).list();

		return j.get(0).getName();

	}

	@Override
	public List<String> getSousGroup(String direction) {

		List<String> groupList = null;
		LdapQuery query = LdapQueryBuilder.query().base("ou=groups,o=mojo").searchScope(SearchScope.SUBTREE)
				.timeLimit(200).countLimit(221).where("objectclass").is("groupOfUniqueNames");
		LdapContextSource lcs = new LdapContextSource();

		lcs.setUrl("ldap://localhost:8389");
		lcs.setUserDn("uid=admin,ou=system");
		lcs.setPassword("secret");
		lcs.setDirObjectFactory(DefaultDirObjectFactory.class);
		lcs.afterPropertiesSet();
		LdapTemplate ldapTemplate = new LdapTemplate(lcs);
		// Attribute attr = attributes.get("cn");
		CourriersArrivésImpl courriersArrivésImpl = new CourriersArrivésImpl();
		if (direction != "Direction Générale")
			groupList = ldapTemplate.list("cn=" + direction + ",cn=Direction Générale,ou=groups,o=mojo");
		else
			groupList = ldapTemplate.list("cn=Direction Générale,ou=groups,o=mojo");
		List<String> sousGroupeName = new ArrayList<String>();
		for (int i = 0; i < groupList.size(); i++) {
			if(groupList.get(i).contains("chefs")){
				continue;	
			}
			else{ 
				sousGroupeName.add(groupList.get(i).substring(groupList.get(i).indexOf("=") + 1));

			}
		
		}
		return sousGroupeName;
	}

}