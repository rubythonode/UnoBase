package kim.uno.base.db;

/**
 * 테이블의 컬럼을 관리하는 클래스
 */
public class Column {
	/**
	 * 컬럼명
	 */
	private String mName = null;
	/**
	 * 컬럼형
	 */
	private String mType = null;
	/**
	 * 컬럼형 사이즈
	 */
	private int mSize = 0;
	/**
	 * Not Null 여부
	 */
	private boolean mNotNull = false;
	/**
	 * 기본값
	 */
	private Object mDefault = null;
	/**
	 * 주키 여부
	 */
	private boolean mPrimary = false;
	/**
	 * 주키 여부
	 */
	private boolean mAutoincrement = false;
	
	/**
	 * 기본 생성자
	 */
	public Column(){
	
	}
	
	/**
	 * 생성자
	 * @param name 컬럼명
	 * @param type 컬럼형
	 */
	public Column(String name, String type){
		this(name, type, 0, false, null);
	}
	
	/**
	 * 생성자
	 * @param name 컬럼명
	 * @param type 컬럼형
	 * @param size 컬럼크기
	 */
	public Column(String name, String type, int size){
		this(name, type, size, false, null);
	}
	
	/**
	 * 생성자
	 * @param name 컬럼명
	 * @param type 컬럼형
	 * @param size 컬럼크기
	 * @param notnull NOT NULL 여부
	 */
	public Column(String name, String type, int size, boolean notnull){
		this(name, type, size, notnull, null);
	}
	
	/**
	 * 생성자
	 * @param name 컬럼명
	 * @param type 컬럼형
	 * @param size 컬럼크기
	 * @param notnull NOT NULL 여부
	 * @param primary 주키 여부
	 */
	public Column(String name, String type, int size, boolean notnull, boolean primary){
		this(name, type, size, notnull);
		setPrimary(primary);
	}
	
	/**
	 * 생성자
	 * @param name 컬럼명
	 * @param type 컬럼형
	 * @param size 컬럼크기
	 * @param notnull NOT NULL 여부
	 * @param primary 주키 여부
	 * @param autoincrement 자동증가여부
	 */
	public Column(String name, String type, int size, boolean notnull, boolean primary, boolean autoincrement){
		this(name, type, size, notnull);
		setPrimary(primary);
		setAutoincrement(autoincrement);
	}
	
	/**
	 * 생성자
	 * @param name 컬럼명
	 * @param type 컬럼형
	 * @param size 컬럼크기
	 * @param notnull NOT NULL 여부
	 * @param def 기본값
	 */
	public Column(String name, String type, int size, boolean notnull, String def){
		setName(name);
		setType(type, size);
		setNotNull(notnull);
		setDefault(def);
	}
	
	/**
	 * 생성자
	 * @param name 컬럼명
	 * @param type 컬럼형
	 * @param size 컬럼크기
	 * @param notnull NOT NULL 여부
	 * @param def 기본값
	 */
	public Column(String name, String type, int size, boolean notnull, int def){
		setName(name);
		setType(type, size);
		setNotNull(notnull);
		setDefault(def);
	}
	
	/**
	 * 생성자
	 * @param name 컬럼명
	 * @param type 컬럼형
	 * @param size 컬럼크기
	 * @param notnull NOT NULL 여부
	 * @param def 기본값
	 * @param primary 주키 여부
	 */
	public Column(String name, String type, int size, boolean notnull, String def, boolean primary){
		this(name, type, size, notnull, def);
		setPrimary(primary);
	}
	
	/**
	 * 생성자
	 * @param name 컬럼명
	 * @param type 컬럼형
	 * @param size 컬럼크기
	 * @param notnull NOT NULL 여부
	 * @param def 기본값
	 * @param primary 주키 여부
	 */
	public Column(String name, String type, int size, boolean notnull, int def, boolean primary){
		this(name, type, size, notnull, def);
		setPrimary(primary);
	}
	
	/**
	 * 컬럼명 설정
	 * @param name 컬럼명
	 */
	public void setName(String name) {
		this.mName = name;
	}
	/**
	 * 컬럼명 반환
	 * @return
	 */
	public String getName() {
		return mName;
	}
	
	/**
	 * 컬럼형 설정
	 * @param type 타입
	 */
	public void setType(String type) {
		this.setType(type, 0);
	}
	/**
	 * 컬럼형 설정
	 * @param type 타입
	 * @param size 크기
	 */
	public void setType(String type, int size) {
		this.mType = type.toUpperCase();
		this.setSize(size);
	}
	
	/**
	 * 컬럼형 반환
	 * @return
	 */
	public String getType() {
		return mType;
	}
	
	/**
	 * 컬럼형 크기 설정
	 * @param size
	 */
	public void setSize(int size) {
		this.mSize = size;
	}
	
	/**
	 * 컬럼형 크기 반환
	 * @return
	 */
	public int getSize() {
		return mSize;
	}
	
	/**
	 * Not Null 설정
	 * @param notnull
	 */
	public void setNotNull(boolean notnull) {
		this.mNotNull = notnull;
	}
	
	/**
	 * Not Null 여부 반환
	 * @return
	 */
	public boolean isNotNull() {
		return mNotNull;
	}
	/**
	 * 기본값 설정
	 * @param def 기본값
	 */
	public void setDefault(String def) {
		this.mDefault = def;
	}
	/**
	 * 기본값 설정
	 * @param def 기본값
	 */
	public void setDefault(int def) {
		this.mDefault = def;
	}
	/**
	 * 기본값 반환
	 * @return
	 */
	public Object getDefault() {
		return mDefault;
	}
	/**
	 * 주키 여부 설정
	 * @param primary
	 */
	public void setPrimary(boolean primary) {
		this.mPrimary = primary;
	}

	/**
	 * 주키 여부
	 * @return
	 */
	public boolean isPrimary() {
		return mPrimary;
	}

	/**
	 * 자동 증가 여부
	 * @return
	 */
	public boolean isAutoincrement() {
		return mAutoincrement;
	}

	/**
	 * 자동 증가 여부
	 * @param mAutoincrement
	 */
	public void setAutoincrement(boolean mAutoincrement) {
		this.mAutoincrement = mAutoincrement;
	}
}
