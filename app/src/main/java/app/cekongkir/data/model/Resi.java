package app.cekongkir.data.model;

public class Resi {
    public static final String TABLE_NAME = "histori_resi";

    public static final String COLUMN_ID        = "id";
    public static final String COLUMN_RESI      = "resi";
    public static final String COLUMN_KURIR     = "kurir";
    public static final String COLUMN_STATUS    = "status";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    private int id;
    private String resi;
    private String kurir;
    private String status;
    private String timestamp;

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_RESI + " TEXT,"
                    + COLUMN_KURIR + " TEXT,"
                    + COLUMN_STATUS + " TEXT,"
                    + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

    public Resi(){

    }

    public Resi(int id, String resi, String kurir, String status, String timestamp){
        this.id         = id;
        this.resi       = resi;
        this.kurir      = kurir;
        this.status     = status;
        this.timestamp  = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getResi() {
        return resi;
    }

    public void setResi(String resi) {
        this.resi = resi;
    }

    public String getKurir() {
        return kurir;
    }

    public void setKurir(String kurir) {
        this.kurir = kurir;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
