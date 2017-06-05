package biz.picosoft.mains;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.DefaultDirObjectFactory;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.query.LdapQueryBuilder;
import org.springframework.ldap.query.SearchScope;

import biz.picosoft.services.CourriersArrivésImpl;

public class TestLdap {
	public static String getAllLdapGroups(){
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
		CourriersArrivésImpl courriersArrivésImpl=new CourriersArrivésImpl();
		
		groupList = ldapTemplate.list("cn=DirectionGénérale,ou=groups,o=mojo");
		List groupListeWithoutRole=new ArrayList<String>();
		  for(int i=0;i<groupList.size();i++){
			  groupList.set(i, groupList.get(i).substring(groupList.get(i).indexOf("=")+1,groupList.get(i).length()));
			  
			if( groupList.get(i).contains("Direction"))
				groupListeWithoutRole.add(groupList.get(i));
			 
		}
		  groupListeWithoutRole.add("DirectionGénérale");
		return groupListeWithoutRole.toString();
				
	}
	

	public static void main(String[] args) {
		System.out.println(getAllLdapGroups()); 
	}
}