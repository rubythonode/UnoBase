package kim.uno.base.db;

import java.util.ArrayList;
import java.util.List;

public class Table {
    public class Index {
        private String mIndexName;
        private List<String> mFields = new ArrayList<String>();
        private boolean mUnique = false;

        /**
         * Index 생성자
         *
         * @param name Index 이름
         */
        public Index(String name) {
            mIndexName = name;
        }

        /**
         * Index 생성자
         *
         * @param name   Index 이름
         * @param fields 필드목록(컬럼목록)
         */
        public Index(String name, String[] fields) {
            this(name);
            for (String field : fields) addField(field);
        }

        /**
         * Index 생성자
         *
         * @param name   Index 이름
         * @param fields 필드목록(컬럼목록)
         * @param unique 유니크 여부
         */
        public Index(String name, String[] fields, boolean unique) {
            this(name, fields);
            setUnique(unique);
        }

        /**
         * Index 이름 반환
         *
         * @return
         */
        public String getName() {
            return mIndexName;
        }

        /**
         * 필드 추가
         *
         * @param field 필드명(컬럼명)
         */
        public void addField(String field) {
            if (!mFields.contains(field)) mFields.add(field);
        }

        /**
         * 필드 목록 반환
         *
         * @return
         */
        public List<String> getFields() {
            return mFields;
        }

        /**
         * 유니크 여부 설정
         *
         * @param unique
         */
        public void setUnique(boolean unique) {
            this.mUnique = unique;
        }

        /**
         * 유니크 여부 반환
         *
         * @return
         */
        public boolean isUnique() {
            return mUnique;
        }

        /**
         * Index 추가 쿼리문
         *
         * @param tableName 테이블명
         * @return
         */
        public String getQuery(String tableName) {
            if (getFields().size() == 0) return null;

            StringBuffer query = new StringBuffer();
            query.append(String.format("CREATE %sINDEX [%s] ON [%s] (", isUnique() ? "UNIQUE " : "", getName(), tableName));
            for (String field : getFields()) {
                query.append(String.format("[%s], ", field));
            }
            query = query.delete(query.length() - 2, query.length());
            query.append(");");

            return query.toString();
        }
    }

    private static List<Table> mTables = new ArrayList<Table>();
    private String mTableName;
    private List<Column> mColumns = new ArrayList<Column>();
    private List<String> mPrimaries = new ArrayList<String>();
    private List<Index> mIndexes = new ArrayList<Index>();

    /**
     * @param name 테이블명
     */
    public Table(String name) {
        this(name, null);
    }

    /**
     * @param name    테이블명
     * @param columns 컬럼목록
     */
    public Table(String name, List<Column> columns) {
        mTableName = name;
        if (columns != null) mColumns = columns;
        mTables.add(this);
    }

    /**
     * 테이블명 반환
     *
     * @return
     */
    public String getName() {
        return mTableName;
    }

    /**
     * 컬럼목록 반환
     *
     * @return
     */
    public List<Column> getColumns() {
        return mColumns;
    }


    /**
     * 테이블 생성 쿼리
     *
     * @return
     */
    public String getCreateQuery() {
        StringBuffer query = new StringBuffer();

        //생성 쿼리
        query.append(String.format("CREATE TABLE [%s] (", getName()));
        query.append(String.format("[_ID] INTEGER PRIMARY KEY AUTOINCREMENT, "));

        //컬럼명
        for (Column column : getColumns()) {
            query.append(String.format("[%s] %s", column.getName(), column.getType()));
            if (column.getSize() > 0) query.append(String.format("(%s)", column.getSize()));
            if (column.isNotNull()) query.append(" NOT NULL");
            if (column.isPrimary()) query.append(" PRIMARY KEY");
            if (column.isAutoincrement()) query.append(" AUTOINCREMENT");
            if (column.getDefault() != null)
                query.append(String.format(" DEFAULT(%s)", column.getDefault() instanceof String ? "'" + column.getDefault() + "'" : column.getDefault()));
            query.append(", ");
            if (column.isPrimary()) {
//				addPrimary(column.getName());
            }
        }

        //주키 설정
        if (getPrimaries().size() > 0) {
            query.append("CONSTRAINT [] PRIMARY KEY (");
            for (String field : getPrimaries()) {
                query.append(String.format("[%s], ", field));
            }
            query = query.delete(query.length() - 2, query.length());
            query.append(")");
        } else {
            query = query.delete(query.length() - 2, query.length());
        }
        query.append(");");

        return query.toString();
    }

    /**
     * 테이블 삭제 쿼리
     *
     * @return
     */
    public String getDropQuery() {
        return String.format("DROP TABLE [%s];", getName());
    }

    /**
     * 모든 TABLE을 리스트로 반환
     *
     * @return
     */
    public static List<Table> getTables() {
        return mTables;
    }

    /**
     * 주키 설정
     *
     * @param fields 필드 목록
     */
    public void setPrimary(String[] fields) {
        this.mPrimaries.clear();
        for (String primary : fields) {
            if (!mPrimaries.contains(primary)) mPrimaries.add(primary);
        }
    }

    /**
     * 주키 필드 추가
     *
     * @param field 필드이름
     */
    public void addPrimary(String field) {
        if (!mPrimaries.contains(field)) mPrimaries.add(field);
    }

    /**
     * 주키 반환
     *
     * @return
     */
    public List<String> getPrimaries() {
        return mPrimaries;
    }

    /**
     * Index 목록 초기화
     */
    public void clearIndexes() {
        mIndexes.clear();
    }

    /**
     * Index 추가
     *
     * @param index Index 인스턴스
     */
    public void addIndex(Index index) {
        if (!mIndexes.contains(index)) mIndexes.add(index);
    }

    /**
     * Index 추가
     *
     * @param name   Index 이름
     * @param fields 필드목록(컬럼명 목록)
     */
    public void addIndex(String name, String[] fields) {
        mIndexes.add(new Index(name, fields));
    }

    /**
     * Index 추가
     *
     * @param name   Index 이름
     * @param fields 필드목록(컬럼명 목록)
     */
    public void addIndex(String name, String[] fields, boolean unique) {
        mIndexes.add(new Index(name, fields, unique));
    }

    /**
     * Index 목록 반환
     *
     * @return
     */
    public List<Index> getIndexes() {
        return mIndexes;
    }
}
