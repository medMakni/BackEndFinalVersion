package biz.picosoft.daoImpl;

import java.util.List;

import org.springframework.stereotype.Component;

import biz.picosoft.dao.SociétéDao;
import biz.picosoft.entity.Contacte;
import biz.picosoft.entity.Société;
@Component
public class SociétéDaoImpl extends GenericDaoImp<Société> implements SociétéDao {
	public List<Contacte> findAllContacts(Société société){
		return société.getContacts();
	}
}
