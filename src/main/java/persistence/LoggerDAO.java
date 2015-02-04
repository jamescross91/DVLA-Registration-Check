package persistence;

import com.google.code.morphia.dao.BasicDAO;
import entities.Logger;
import org.bson.types.ObjectId;

public class LoggerDAO extends BasicDAO<Logger, ObjectId> {
	public LoggerDAO() {
		super(Logger.class, MongoConnectionManager.instance().getDb());
	}
}
