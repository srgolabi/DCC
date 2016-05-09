package agtp.dataBase;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.RawRowMapper;
import com.j256.ormlite.misc.TransactionManager;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author reza
 */
public class BaseRepo<T, ID> {

    protected Dao<T, ID> dao;

    public BaseRepo(ConnectionSource connectionSource, Class<T> clazz) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, clazz);
            dao = DaoManager.createDao(connectionSource, clazz);
        } catch (SQLException ex) {
            System.out.println(" !!! error !!! " + ex.getMessage());
            Logger.getLogger(BaseRepo.class.getName()).log(Level.SEVERE, null, ex.getMessage());
        }
    }

    public Dao<T, ID> getDao() {
        return this.dao;
    }

    public int create(T t) {
        try {
            return dao.create(t);
        } catch (SQLException e) {
        }
        return 0;
    }

    public boolean insertList(List<T> tList) throws SQLException {
        int insertedCount = 0;
        TransactionManager.callInTransaction(dao.getConnectionSource(), () -> {
            int insertCount = 0;
            for (T t : tList) {
                try {
                    insertCount += dao.createOrUpdate(t).getNumLinesChanged();
                } catch (SQLException e) {
                    System.out.println("" + e.getMessage());
                    Logger.getLogger(BaseRepo.class.getName()).log(Level.SEVERE, null, e);
                }
            }
            return insertCount;
        });
        return tList.size() == insertedCount;

    }

    public int update(T t) {
        try {
            return dao.update(t);
        } catch (SQLException e) {
        }
        return 0;
    }

    public int delete(T t) {
        try {
            return dao.delete(t);
        } catch (SQLException e) {
        }
        return 0;
    }

    public void delete(List<T> tList) throws SQLException {
        try {
            TransactionManager.callInTransaction(dao.getConnectionSource(), () -> {
                int insertCount = 0;
                for (T t : tList) {
                    dao.delete(t);
                }
                return insertCount;
            });
        } catch (Exception e) {
        }

    }

    public List<T> getAll() {
        try {
            return dao.queryForAll();
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
        return new ArrayList<>();
    }

    public List<T> rawResults(String query) {
        try {
            return dao.queryRaw(query, dao.getRawRowMapper()).getResults();
        } catch (SQLException ex) {
            System.out.println("e = " + ex.getMessage());
            Logger.getLogger(BaseRepo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ArrayList<>();
    }

    public List<String> rawResultsString(String query) {
        try {
            return dao.queryRaw(query, new RawRowMapper<String>() {

                @Override
                public String mapRow(String[] columnNames, String[] resultColumns) throws SQLException {
                    return resultColumns[0];
                }
            }).getResults();
        } catch (SQLException ex) {
            Logger.getLogger(BaseRepo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ArrayList<>();
    }

    public List<T> getAllNotNull(String field) {
        try {
            return dao.queryBuilder().where().isNotNull(field).query();
        } catch (SQLException ex) {
            Logger.getLogger(BaseRepo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<T> getAllNull(String field) {
        try {
            return dao.queryBuilder().where().isNull(field).query();
        } catch (SQLException ex) {
            Logger.getLogger(BaseRepo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<T> getAll(String field, Object value) {
        List<T> result = new ArrayList<>();
        try {
            result = dao.query(queryBuilder().where().eq(field, value).prepare());
        } catch (SQLException ex) {
            Logger.getLogger(BaseRepo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public T getFirst(String field, Object value) {
        try {
            return dao.queryBuilder().where().eq(field, value).queryForFirst();
        } catch (SQLException ex) {
            Logger.getLogger(BaseRepo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public T queryForId(ID id) {
        try {
            return dao.queryForId(id);
        } catch (SQLException e) {
        }
        return null;
    }

    public Dao.CreateOrUpdateStatus createOrUpdate(T t) {
        try {
            return dao.createOrUpdate(t);
        } catch (SQLException e) {
        }
        return new Dao.CreateOrUpdateStatus(false, false, 0);
    }

    public QueryBuilder<T, ID> queryBuilder() {
        return dao.queryBuilder();
    }

    public T getEnd() {
        try {
            return getFirst("id", dao.countOf());
        } catch (SQLException ex) {
            return null;
        }
    }

    public synchronized void removeAll() {
        try {
            dao.delete(dao.queryForAll());
        } catch (SQLException e) {
        }
    }
}
