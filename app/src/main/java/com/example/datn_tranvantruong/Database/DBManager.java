package com.example.datn_tranvantruong.Database;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;

import com.example.datn_tranvantruong.Model.AddProduct;
import com.example.datn_tranvantruong.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DBManager extends SQLiteOpenHelper {

    // Tên cơ sở dữ liệu
    private static final String DATABASE_NAME = "BOOK_TOUR.db";
    // Phiên bản cơ sở dữ liệu
    private static final int DATABASE_VERSION = 1;

    // Tên bảng
    private static final String TABLE_CUSTOMER = "customers";
    private static final String TABLE_ADMIN = "admin";
    private static final String TABLE_CATEGORIES = "categories";
    private static final String TABLE_PRODUCTS = "products";
    private static final String TABLE_CARTS = "carts";
    private static final String TABLE_BILLS = "bills";


    // Các trường trong bảng Users
    private static final String COLUMN_USER_ID = "id";
    private static final String COLUMN_USER_EMAIL = "email";
    private static final String COLUMN_USER_FULLNAME = "fullname";
    private static final String COLUMN_USER_PASSWORD = "password";
    private static final String COLUMN_USER_ADDRESS = "address";
    private static final String COLUMN_USER_AVATAR = "image_avatar";
    private static final String COLUMN_USER_PHONE = "phone";

    // Các trường trong bảng Admin
    private static final String COLUMN_ADMIN_ID = "id";
    private static final String COLUMN_ADMIN_EMAIL = "email";
    private static final String COLUMN_ADMIN_PASSWORD = "password";

    // Các trường trong bảng Categories
    private static final String COLUMN_CATEGORY_ID = "id";
    private static final String COLUMN_CATEGORY_NAME = "name";
    private static final String COLUMN_CATEGORY_IMAGE = "image";


    // Các trường trong bảng Products
    private static final String COLUMN_PRODUCT_ID = "id";
    private static final String COLUMN_PRODUCT_CATEGORY_ID = "category_id";
    private static final String COLUMN_PRODUCT_NAME = "name";
    private static final String COLUMN_PRODUCT_STARTDATE = "startdate";
    private static final String COLUMN_PRODUCT_ENDDATE = "enddate";
    private static final String COLUMN_PRODUCT_DESCRIPTION = "description";
    private static final String COLUMN_PRODUCT_LOCATION = "location";
    private static final String COLUMN_PRODUCT_PRICE = "price";
    private static final String COLUMN_PRODUCT_IMAGE = "image";

    // Các trường trong bảng Carts
    private static final String COLUMN_CART_ID = "id";
    private static final String COLUMN_CART_USER_ID = "user_id";
    private static final String COLUMN_CART_PRODUCT_ID = "product_id";
    private static final String COLUMN_CART_QUANTITY = "quantity";
    private static final String COLUMN_CART_PRICE = "price";

    // Các trường trong bảng Bills
    private static final String COLUMN_BILL_ID = "id";
    private static final String COLUMN_BILL_USER_ID = "user_id";
    private static final String COLUMN_BILL_PRODUCT_ID = "product_id";
    private static final String COLUMN_BILL_PRODUCT_QUATITY = "quatity";
    private static final String COLUMN_BILL_TOTAL_PRICE = "total_price";
    private static final String COLUMN_BILL_DESCRIPTION = "description";
    private static final String COLUMN_BILL_DATE_CREATED = "date_created";

Context context;
    // Câu lệnh SQL để tạo bảng Users
    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_CUSTOMER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USER_EMAIL + " TEXT UNIQUE,"
            + COLUMN_USER_FULLNAME + " TEXT UNIQUE,"
            + COLUMN_USER_PASSWORD + " TEXT,"
            + COLUMN_USER_ADDRESS + " TEXT,"
            + COLUMN_USER_AVATAR + " BLOB,"
            + COLUMN_USER_PHONE + " TEXT"
            + ")";
    // Câu lệnh SQL để tạo bảng Admin
    private static final String CREATE_TABLE_ADMIN = "CREATE TABLE " + TABLE_ADMIN + "("
            + COLUMN_ADMIN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_ADMIN_EMAIL + " TEXT,"
            + COLUMN_ADMIN_PASSWORD + " TEXT"
            + ")";

    // Câu lệnh SQL để tạo bảng Categories
    private static final String CREATE_TABLE_CATEGORIES = "CREATE TABLE " + TABLE_CATEGORIES + "("
            + COLUMN_CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_CATEGORY_NAME + " TEXT UNIQUE"
            + ")";

    // Câu lệnh SQL để tạo bảng Products
    private static final String CREATE_TABLE_PRODUCTS = "CREATE TABLE " + TABLE_PRODUCTS + "("
            + COLUMN_PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_PRODUCT_CATEGORY_ID + " INTEGER,"
            + COLUMN_PRODUCT_NAME + " TEXT,"
            + COLUMN_PRODUCT_STARTDATE + " TEXT,"
            + COLUMN_PRODUCT_ENDDATE + " TEXT,"
            + COLUMN_PRODUCT_DESCRIPTION +" TEXT,"
            + COLUMN_PRODUCT_LOCATION + " TEXT,"
            + COLUMN_PRODUCT_PRICE + " REAL,"
            + COLUMN_PRODUCT_IMAGE + " BLOB,"
            + "FOREIGN KEY(" + COLUMN_PRODUCT_CATEGORY_ID + ") REFERENCES " + TABLE_CATEGORIES + "(" + COLUMN_CATEGORY_ID + ")"
            + ")";

    // Câu lệnh SQL để tạo bảng cart
    private static final String CREATE_TABLE_CARTS = "CREATE TABLE " + TABLE_CARTS + "("
            + COLUMN_CART_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_CART_USER_ID + " INTEGER,"
            + COLUMN_CART_PRODUCT_ID + " INTEGER,"
            + COLUMN_CART_QUANTITY + " INTEGER,"
            + COLUMN_CART_PRICE + " REAL,"
            + "FOREIGN KEY(" + COLUMN_CART_USER_ID + ") REFERENCES " + TABLE_CUSTOMER + "(" + COLUMN_USER_ID + "), "
            + "FOREIGN KEY(" + COLUMN_CART_PRODUCT_ID + ") REFERENCES " + TABLE_PRODUCTS + "(" + COLUMN_PRODUCT_ID + ")"
            + ")";


    // Câu lệnh SQL để tạo bảng Bills
    private static final String CREATE_TABLE_BILLS = "CREATE TABLE " + TABLE_BILLS + "("
            + COLUMN_BILL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_BILL_USER_ID + " INTEGER,"
            + COLUMN_BILL_PRODUCT_ID + " INTEGER,"
            + COLUMN_BILL_PRODUCT_QUATITY + " INTEGER,"
            + COLUMN_BILL_TOTAL_PRICE + " REAL,"
            + COLUMN_BILL_DESCRIPTION + " TEXT,"
            + COLUMN_BILL_DATE_CREATED + " DATETIME DEFAULT (datetime('now')),"
            + "FOREIGN KEY(" + COLUMN_BILL_USER_ID + ") REFERENCES " + TABLE_CUSTOMER + "(" + COLUMN_USER_ID + ")"
            + ")";


    public DBManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
         // Set the class variable with the provided context
    }
    private static final String INSERT_ADMIN = "INSERT INTO admin (" + COLUMN_ADMIN_EMAIL + ", " + COLUMN_ADMIN_PASSWORD + ") VALUES ( 'admin', '270801')";
    private static final String INSERT_CATEGORY = "INSERT INTO categories (" + COLUMN_CATEGORY_NAME + ") VALUES " +
            "('TOUR TRONG NƯỚC'), " +
            "('TOUR NƯỚC NGOÀI'), " +
            "('TOUR THEO ĐOÀN'), " +
            "('TOUR GIÁ RẺ'), " +
            "('TOUR TẾT 2024')";



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_ADMIN);
        db.execSQL(CREATE_TABLE_CATEGORIES);
        db.execSQL(CREATE_TABLE_PRODUCTS);
        db.execSQL(CREATE_TABLE_BILLS);
        db.execSQL(CREATE_TABLE_CARTS);
        db.execSQL(INSERT_ADMIN);
        db.execSQL(INSERT_CATEGORY);
        List<AddProduct> sampleProducts = generateSampleProducts(context);
        for (AddProduct product : sampleProducts) {
            insertProduct(db, product);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CUSTOMER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADMIN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BILLS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CARTS);

        onCreate(db);
    }

    public long insertProduct(SQLiteDatabase db, AddProduct product) {
        ContentValues values = new ContentValues();

        values.put(COLUMN_PRODUCT_CATEGORY_ID, product.getCategoryId());
        values.put(COLUMN_PRODUCT_NAME, product.getName());
        values.put(COLUMN_PRODUCT_STARTDATE, product.getStartdate());
        values.put(COLUMN_PRODUCT_ENDDATE, product.getEnddate());
        values.put(COLUMN_PRODUCT_DESCRIPTION, product.getDescription());
        values.put(COLUMN_PRODUCT_LOCATION, product.getLocation());
        values.put(COLUMN_PRODUCT_PRICE, product.getPrice());
        values.put(COLUMN_PRODUCT_IMAGE, product.getImage());

        return db.insert(TABLE_PRODUCTS, null, values);
    }

    public static List<AddProduct> generateSampleProducts(Context context) {
        List<AddProduct> productList = new ArrayList<>();

        productList.add(new AddProduct(1, "Tour Châu Đốc 1 Ngày 1 Đêm | Miếu Bà, Trà Sư, Núi Cấm", "26/11/2023", "26/11/2023",
                "CHƯƠNG TRÌNH TOUR\n" +
                        "ĐÊM | TP. HỒ CHÍ MINH - CHÂU ĐỐC - AN GIANG (Nghỉ đêm trên xe)\n" +
                        "Tối: 21h00 Xe và hướng dẫn viên Vietnam Booking đón quý khách tại điểm hẹn, khởi hành đưa đoàn rời Sài Gòn đi An Giang để bắt đầu hành trình khám phá Tour Châu Đốc 1 Ngày 1 Đêm.\n" +
                        "\n" +
                        "Đoàn đi qua địa phận các tỉnh miền Tây gồm Long An, Tiền Giang, Đồng Tháp, HDV sẽ thuyết minh, giới thiệu cho quý du khách về tín ngưỡng thờ Bà Chúa Xứ ở Nam Bộ.\n" +
                        "\n" +
                        "Đoàn nghỉ đêm trên xe.\n" +
                        "\n" +
                        "SÁNG | KHÁM PHÁ CHÂU ĐỐC - MIẾU BÀ CHÚA XỨ - RỪNG TRÀM TRÀ SƯ (Ăn sáng, trưa)\n" +
                        "Sáng: 05h00 Đoàn tới thành phố Châu Đốc, dừng chân nghỉ ngơi và bắt đầu hành trình tour Châu Đốc Chùa Bà 1 ngày với việc đi thăm viếng miếu Bà Chúa Xứ - Công trình có kiến trúc đẹp và tôn nghiêm tọa lạc dưới chân núi Sam nổi tiếng. Tại đây quý khách có thể dâng hương, cầu nguyện cho sức khỏe dồi dào và gặp nhiều may mắn. Bên cạnh đó, quý khách còn được thưởng ngoạn phong cảnh nên thơ, khí hậu trong lành, cây cối xanh tươi và khung cảnh núi rừng bao la, trùng điệp đẹp rung động lòng người của Châu Đốc.\n" +
                        "\n" +
                        "Tại đây, HDV còn đưa đoàn ghé thăm:\n" +
                        "\n" +
                        "Tây An Cổ Tự: Ngôi chùa mang phong cách nghệ thuật Ấn Độ độc đáo kết hợp với kiến trúc Việt Nam cổ xưa\n" +
                        "Lăng Thoại Ngọc Hầu: Là di tích lịch sử cấp quốc gia và là một trong những công trình hiếm hoi còn nguyên vẹn từ thời Nguyễn ở đất Tây Nam Bộ. \n" +
                        "07h00: Đoàn ăn sáng tại Châu Đốc.\n" +
                        "\n" +
                        "Sau đó xe và HDV đưa đoàn di chuyển qua khu vực Núi Cấm – vùng đất linh thiêng của An Giang. Tại đây quý khách có thể tự do chọn phương tiện lên núi bằng cáp treo hoặc xe máy (chi phí tự túc). Núi Cấm được mệnh danh là Đà Lạt ở miền Tây bởi không khí mát lành, phong cảnh yên bình nên thơ. Quý khách có thể viếng cảnh và chụp ảnh lưu niệm tại Chùa Vạn Linh, Chùa Phật lớn, ngoạn cảnh hồ Thủy Liêm.\n" +
                        "\n" +
                        "Nếu không lên Núi Cấm, xe sẽ đưa quý khách đi tham quan Tour Châu Đốc Rừng Tràm Trà Sư, đến với khu rừng rừng ngập nước tiêu biểu cho vùng Tây sông Hậu và là điểm đến không thể bỏ qua khi du lịch miền Tây (chi phí đi xuồng tự túc).\n" +
                        "\n" +
                        "Trưa 11h00 Đoàn dùng trưa tại nhà hàng và chia tay An Giang. Tiếp tục ghé thăm:\n" +
                        "\n" +
                        "Làng hoa Sa Đéc Đồng Tháp:  Tọa lạc tại huyện Sa Đéc tỉnh Đồng Tháp, làng hoa hiện đang trở thành điểm đến thu hút, với diện tích trồng hoa lên tới 100ha, hứa hẹn sẽ đem đến cho bạn những trải nghiệm thú vị và có những bộ ảnh sống ảo cực chất\n" +
                        "CHIỀU | CHÂU ĐỐC - VỀ LẠI THÀNH PHỐ HỒ CHÍ MINH \n" +
                        "Chiều: 16h00 Đoàn tạm biệt Làng Hoa Sa Đéc để đến với điểm tham quan tiếp theo trong chương trình (nếu còn thời gian):\n" +
                        "\n" +
                        "Nhà cổ Huỳnh Thủy Lê: Công trình kiến trúc độc đáo bật nhất vùng Nam Bộ theo phong cách Đông - Tây. Đây là nơi sinh sồng của ông Huỳnh Thủy Lê, nhân vật nam chính trong cuốn tiểu thuyết “Người tình”.\n" +
                        "Đoàn di chuyển lên xe và khởi hành về lại Tp. Hồ Chí Minh. Trên đường về đoàn tự do nghỉ ngơi và dùng cơm tối ở trạm dừng chân trước khi lên cao tốc.\n" +
                        "\n" +
                        "Tối: Khoảng 20h30 Đoàn về tới TP.HCM. Xe đưa quý khách về điểm đón ban đầu. Kết thúc tour Châu Đốc 1 Ngày 1 Đêm đáng nhớ, HDV chia tay và hẹn gặp lại quý khách trong những chương trình tour du lịch giá rẻ sau!\n" +
                        "\n" +
                        "LƯU Ý: Thứ tự và chi tiết trong chương trình có thể thay đổi cho phù hợp với tình hình thực tế, nhưng vẫn đảm bảo đủ điểm đến tham quan cho quý du khách!", "Hồ Chí Minh", 990000, getImageBytes(context, R.drawable.tn1)));

        productList.add(new AddProduct(1, "Tour du lịch Hành Hương: Chùa Bà Châu Đốc – Núi Cấm 1 ngày 1 đêm", "26/11/2023", "26/11/2023",
                "CHƯƠNG TRÌNH TOUR\n" +
                        "LỊCH TRÌNH TOUR HÀNH HƯƠNG CHÙA BÀ CHÂU ĐỐC 1 NGÀY 1 ĐÊM\n" +
                        "ĐÊM | TP. HỒ CHÍ MINH - CHÂU ĐỐC (NGHỈ ĐÊM TRÊN XE)\n" +
                        "22h00: Xe và hướng dẫn viên (HDV) Vietnam Booking đón quý khách tại điểm hẹn, khởi hành từ Sài Gòn đi An Giang bắt đầu hành trình tour Chùa Bà Châu Đốc 1 ngày 1 đêm ghé thăm Miếu Bà Chúa Xứ, Núi Cấm, Chợ Tịnh Biên.\n" +
                        "\n" +
                        "Các điểm đón khách:\n" +
                        "\n" +
                        "Cây Xăng Comeco – Ngã 4 Hàng Xanh, Quận Bình Thạnh, TP Hồ Chí Minh\n" +
                        "Nhà Văn Hóa Thanh Niên – Số 4 Phạm Ngọc Thạch, Quận 1 – TP Hồ Chí Minh\n" +
                        "Galaxy Kinh Dương Vương – Quận 6\n" +
                        "Siêu thị Bảy Mập – Đối diện Bến xe Miền Tây\n" +
                        "Quý khách đi qua địa phận các tỉnh miền Tây như Long An, Tiền Giang, Đồng Tháp; HDV sẽ thuyết minh, giới thiệu cho quý du khách về nét đặc trưng văn hóa miền Tây sông nước và tín ngưỡng thờ Bà Chúa Xứ ở Nam Bộ.\n" +
                        "\n" +
                        "Đoàn nghỉ đêm trên xe.\n" +
                        "\n" +
                        "SÁNG | KHÁM PHÁ CHÂU ĐỐC - MIẾU BÀ CHÚA XỨ - CHỢ TỊNH BIÊN (ĂN SÁNG, TRƯA)\n" +
                        "04h00: Đoàn tới thành phố Châu Đốc, dừng chân nghỉ ngơi và khởi hành đi:\n" +
                        "\n" +
                        "Miếu Bà Chúa Xứ: Quý khách viếng miếu Bà nổi tiếng linh thiêng và kiến trúc độc đáo, tọa lạc tại chân núi Sam. Tại đây bạn có thể cầu bình an, may mắn cho gia đình; chiêm ngưỡng kiến trúc và cảnh đẹp nên thơ xung quanh.\n" +
                        "Tây An Cổ Tự: Cũng tọa lạc tại chân núi Sam, chùa sẽ khiến du khách ngỡ ngàng trước vẻ cổ kính mang phong cách nghệ thuật Ấn Độ và thiên nhiên trù phú.\n" +
                        "Lăng Thoại Ngọc Hầu: Di tích lịch sử cấp quốc gia và thuộc một trong những công trình hiếm hoi còn nguyên vẹn từ thời Nguyễn ở đất Tây Nam Bộ. Đến Lăng Thoại Ngọc Hầu, Quý khách có thêm nhiều hiểu biết về một nhân vật tài ba đã có công khai mở đất An Giang linh thiêng và trù phú như ngày nay.\n" +
                        "06h30: Tiếp tục tour hành hương Chùa Bà Châu Đốc, quý khách đến nhà hàng dùng điểm tâm sáng và ghé chợ Châu Đốc - khu chợ nổi tiếng khi đi du lịch miền Tây, mua các đặc sản về làm quà như: mắm Châu Đốc, đường thốt nốt...\n" +
                        "\n" +
                        "07h30: Sau khi dùng bữa sáng, đoàn rời Châu Đốc đi núi Cấm. Để chinh phục được núi Cấm Quý khách sẽ sử dụng cáp treo hiện đại để ngắm cảnh thiên nhiên miền Tây trù phú, viếng chùa Vạn Linh, chùa Phật Lớn - nơi có tượng Phật Di Lặc cao 32m (Chi phí cáp treo tự túc).\n" +
                        "\n" +
                        "Quý khách có thể ngắm cảnh hồ Thủy Liêm hoặc thuê xe chở đi tham quan các địa điểm: Vồ Thiên Tuế, động Thủy Liêm, vồ Bồ Hông, suối Thanh Long, di tích vua Gia Long, điện 13 tầng, hang Bác vật lang, hang Ông Thẻ hang Ông Hổ, điện Rau Tần…\n" +
                        "\n" +
                        "10h30: Đoàn rời Châu Đốc đi chợ Tịnh Biên, trên đường đi Quý khách sẽ ngắm phong cảnh Bảy Núi xinh đẹp và xanh mượt đặc trưng vùng sông nước, thưởng thức hương vị ngọt thanh của trái thốt nốt tại điểm dừng chân.\n" +
                        "\n" +
                        "Đến nơi, Quý khách dừng chân tham quan và mua sắm tại khu chợ cửa khẩu Tịnh Biên với các mặt hàng nhập từ Campuchia và Thái Lan có giá cả rất phải chăng.  \n" +
                        "\n" +
                        "11h30: Quý khách dùng cơm trưa và nghỉ ngơi, thư giãn trong phong cảnh thiên nhiên hữu tình với rừng xanh, suối trắng.\n" +
                        "\n" +
                        "CHIỀU | CHÂU ĐỐC - VỀ LẠI TP. HỒ CHÍ MINH \n" +
                        "13h00: Đoàn khởi hành về lại Thành phố Hồ Chí Minh.\n" +
                        "\n" +
                        "Tối: Dự kiến 20h00 đoàn về tới Thành phố Hồ Chí Minh. Xe đưa Quý khách về điểm đón ban đầu. Kết thúc tour Chùa Bà Châu Đốc núi Cấm 1 ngày 1 đêm với nhiều kỷ niệm, HDV chia tay và hẹn gặp lại quý khách trong những chương trình tour du lịch giá rẻ sau!\n" +
                        "\n" +
                        "LƯU Ý: Thứ tự và chi tiết trong chương trình có thể thay đổi cho phù hợp với tình hình thực tế, nhưng vẫn đảm bảo đủ điểm đến tham quan cho quý du khách!", "Hồ Chí Minh", 890000, getImageBytes(context, R.drawable.tn2)));

        productList.add(new AddProduct(1, "Tour Củ Chi Mekong 1 ngày giá tốt | Củ Chi – Cù Lao Thới Sơn", "26/11/2023", "26/11/2023",
                "CHƯƠNG TRÌNH TOUR\n" +
                        "Sáng| TP.HCM - ĐỊA ĐẠO CỦ CHI (ĂN TRƯA)\n" +
                        "Sáng: HDV đón quý khách tại Công ty Vietnam Booking để bắt đầu tour Củ Chi Mekong 1 ngày. Đầu tiên là khởi hành đến Địa Đạo Củ Chi, cách TP.HCM khoảng 70km về phía Tây Bắc. Hệ thống địa đạo Củ Chi được xây dựng trên “vùng đất thép”, nằm ở điểm cuối cùng trên Đường mòn Hồ Chí Minh. Đến đây, quý khách sẽ được hóa thân thành các chiến sĩ cách mạng để được trải nghiệm một số hoạt động vô cùng thú vị:\n" +
                        "\n" +
                        "Trong quá trình tham quan địa đạo Củ Chi, quý khách sẽ được tìm hiểu cách xây địa đạo cũng như phương thức chiến đấu, sinh sống dưới địa đạo.\n" +
                        "Tận mắt chứng kiến những vũ khí thô sơ tự chế, bẫy chông bằng tre,...\n" +
                        "Bò trườn trong các đường địa đạo, xem người dân địa phương làm bánh tráng và rượu gạo.\n" +
                        "Trải nghiệm cơ hội bắn súng tại trường tập bắn ở Địa Đạo Củ Chi.\n" +
                        "Trưa: Cả đoàn dùng cơm trưa tại nhà hàng địa phương ở Củ Chi.\n" +
                        "\n" +
                        "CHIỀU | CẢNG DU THUYỀN MỸ THO - CÙ LAO THỚI SƠN - ĐỜN CA TÀI TỬ\n" +
                        "Sau đó, tiếp tục khởi hành hành trình đến vùng sông nước Đồng Bằng Sông Cửu Long. HDV sẽ dẫn quý khách đến một số điểm tham quan nổi tiếng trong tour Củ Chi Mekong 1 ngày.\n" +
                        "\n" +
                        "Đến Cảng Du Thuyền Mỹ Tho, cả đoàn lên tàu để du ngoạn trên sông Tiền, ngắm cảnh bốn cù lao Long, Lân, Qui, Phụng. Khi thuyền chạy dọc theo bè cá nổi, quý khách sẽ được lắng nghe cách nuôi cá trên sông của người dân địa phương. Tiếp tục cuộc hành trình, quý khách sẽ tận mắt chiêm ngưỡng Cầu Rạch Miễu.\n" +
                        "Đến cù lao Thới Sơn (cồn Lân), quý khách tản bộ trên con đường làng, tham quan nhà dân và vườn trái cây. Sau đó, quý khách chụp ảnh lưu niệm rồi tham quan trại nuôi ong mật. Đến nơi đây, quý khách sẽ được thưởng thức trà mật ong chanh, khám phá lò sản xuất kẹo dừa trứ danh.\n" +
                        "Tiếp tục, cả đoàn sẽ cùng nhau lắng nghe giai điệu Đờn ca tài tử, ăn trái cây miễn phí. Sau đó, quý khách sẽ chèo xuồng lẻn lỏi vào rạch nhỏ để ngắm nhìn hai hàng dừa nước thiên nhiên và phong cảnh giản bình dị của miền Tây miệt vườn.\n" +
                        "Chiều: Khởi hành về lại TP.HCM để kết thúc tour Củ Chi Mekong 1 ngày \n" +
                        "\n" +
                        "Cả đoàn sẽ trở lại thuyền về Mỹ Tho để khởi hành về lại TP.HCM.\n" +
                        "Đến TPHCM, cả đoàn sẽ nói lời chào tạm biệt tại Phạm Ngũ Lão hoặc chợ Bến Thành. HDV chia tay và hẹn gặp lại quý khách trong những lần sau!\n" +
                        "Lưu ý: Thứ tự và chi tiết trong chương trình có thể thay đổi cho phù hợp với tình hình thực tế, nhưng vẫn đảm bảo đủ điểm đến tham quan!", "Hồ Chí Minh", 1590000, getImageBytes(context, R.drawable.tn3)));

        productList.add(new AddProduct(1, "Tour Hạ Long 1 Ngày từ Hà Nội | Vịnh Hạ Long – Động Thiên Cung – Hòn Gà Chọi", "26/11/2023", "26/11/2023",
                "CHƯƠNG TRÌNH TOUR\n" +
                        "LỊCH TRÌNH TOUR HẠ LONG 1 NGÀY\n" +
                        "SÁNG | HÀ NỘI – TUẦN CHÂU - HẠ LONG\n" +
                        "07h30 – 08h15: Xe & Hướng Dẫn Viên (HDV) Vietnam Booking sẽ đón Quý khách tại khách sạn trong trung tâm khu vực Phố cổ - quận Hoàn Kiếm, Hà Nội, quý khách lên xe khởi hành đi tour du lịch Hạ Long 1 ngày. Đoàn sẽ dừng chân nghỉ ngơi 20p tại Hải Dương.\n" +
                        "\n" +
                        "Tiếp tục hành trình tour Hạ Long, quý khách đến cảng tàu du lịch quốc tế Tuần Châu. Tại đây, HDV làm thủ tục đưa Quý khách lên tàu ra tham quan khám phá Vịnh Hạ Long - Di sản Thiên Nhiên Thế Giới hai lần được Unessco công nhận với các giá trị địa chất, địa mạo, đa dạng sinh học, giá trị thẩm mỹ…\n" +
                        "\n" +
                        "Trưa: Quý khách thưởng thức đồ uống chào mừng và ăn bữa trưa trên tàu trong khi tàu di chuyển trên vịnh Hạ Long len lỏi qua các hòn đảo đá vôi với những hình thú kỳ bí. Sau đó, tàu đưa Quý khách đến với Động Thiên Cung, một trong những hang động đẹp nhất trên vịnh Hạ Long với vô vàn nhũ đá, măng đá mang những hình thù kỳ lạ.\n" +
                        "\n" +
                        "CHIỀU | KHÁM PHÁ ĐỘNG THIÊN CUNG -  VỊNH HẠ LONG - HÀ NỘI\n" +
                        "Tiếp tục hành trình, Quý khách được chiêm ngưỡng, lưu lại những tấm hình tuyệt đẹp tại hòn Chó Đá, hòn Đỉnh Hương, hòn Gà Chọi...\n" +
                        "\n" +
                        "Tàu dừng tại khu vực Ba Hang tại đây Quý khách có thể tham gia dịch vụ chèo kayak hoặc bamboo boat khám phá hang động trên Vịnh Hạ Long (nếu quý khách đặt trước dịch vụ).\u200B\n" +
                        "\n" +
                        "Tàu cập bến Tuần Châu, xe đón Quý khách quay trở về Hà Nội. Trên đường dừng chân nghỉ ngơi 20 phút tại Hải Dương.\n" +
                        "\n" +
                        "Tối: Đoàn về đến Hà Nội, Xe & HDV đưa quý khách tại điểm đón ban đầu. Kết thúc chương trình tour du lịch Hạ Long 1 ngày, hẹn gặp lại quý khách vào những tour du lịch giá rẻ tiếp theo !\n" +
                        "\n" +
                        "LƯU Ý: Thứ tự và chi tiết trong chương trình có thể thay đổi cho phù hợp với tình hình thực tế, nhưng vẫn đảm bảo đủ điểm đến tham quan !", "Hà Nội", 1090000, getImageBytes(context, R.drawable.tn4)));

        productList.add(new AddProduct(1, "Tour du lịch Hà Giang 3 ngày 2 đêm | Đồng Văn – Sông Nho Quế", "26/11/2023", "26/11/2023",
                "CHƯƠNG TRÌNH TOUR\n" +
                        "CHƯƠNG TRÌNH TOUR HÀ GIANG 3 NGÀY 2 ĐÊM\n" +
                        "Ngày 1: Hà Nội – Quản Bạ - Yên Minh – Đồng Văn\n" +
                        "5h30: Xe và hướng dẫn viên công ty du lịch Vietnam Booking đón du khách tại điểm hẹn, khởi hành tour du lịch Hà Giang 3 ngày 2 đêm. Hà Giang là tỉnh cực bắc của Việt Nam, nơi đây có chè san, rượu mật ong, thắng cố, có hoa lê, mỗi độ xuân về lại rực sắc của đào phai. Trên đường đi, đoàn dừng chân dùng điểm tâm sáng tự túc tại chân đường cao tốc Hà Nội - Lào Cai.\n" +
                        "\n" +
                        "12h00: Du khách đến thành phố Hà Giang, dùng bữa trưa tại nhà hàng.\n" +
                        "\n" +
                        "13h30: Đoàn tiếp tục hành trình đến Hà Giang – Cổng trời Quản Bạ - Núi đôi Cô Tiên. Trên đường đi, du khách sẽ được dịp ngắm nhìn ngọn dốc Bắc Xum dài 7km hùng vỹ. Đoàn đến Quản Bạ, chiêm ngưỡng núi đôi Cô Tiên – một kiệt tác mà tạo hóa đã ban tặng cho mảnh đất địa đầu Tổ quốc. Tại đây bạn còn được nghe kể những câu chuyện cũng như truyền thuyết vô cùng thú vị, hấp dẫn về ngọn núi này.\n" +
                        "\n" +
                        "Du khách tiếp tục di chuyển đến cao nguyên đá Đồng Văn – diện tích trải rộng trên bốn huyện Quản Bạ, Yên Minh, Đồng Văn, Mèo Vạc. Nơi đây đã được UNESCO công nhận là công viên địa chất toàn cầu. Danh hiệu này chỉ có duy nhất tại Việt Nam và thứ 2 ở Đông Nam Á.\n" +
                        "\n" +
                        "18h30: Du khách dùng bữa tối tại nhà hàng và thưởng thức các món đặc sản vùng cao Đông Bắc. Nghỉ đêm tại nhà nghỉ ở Đồng Văn. \n" +
                        "\n" +
                        "Ngày 2: Cao nguyên đá Đồng Văn – Cột cờ Lũng Cú – Yên Minh\n" +
                        "06h00: Sau bữa sáng, đoàn làm thủ tục trả phòng khách sạn. Tiếp tục lịch trình tour du lịch Hà Giang 3 ngày 2 đêm, xe và hướng dẫn viên đưa đoàn đi tham quan đèo Mã Pí Lèng – đường đèo hiểm trở nhất trong “tứ đại đỉnh đèo” của Việt Nam. Tại đây, du khách sẽ được chiêm ngưỡng vẻ đẹp tuyệt vời của đỉnh Mã Pí Lèng với dòng sông Nho Quế uốn lượn quanh co.\n" +
                        "\n" +
                        "Đoàn tiếp tục khởi hành đi Lũng Cú – điểm cực Bắc của Tổ quốc. Tại đây, du khách chinh phục cột cờ Lũng Cú, ngắm nhìn vẻ đẹp hùng vĩ của núi rừng và tận hưởng bầu không khí trong lành, mát lạnh.\n" +
                        "\n" +
                        "12h30: Du khách dùng bữa trưa tại nhà hàng ở Dinh thự Vua Mèo.\n" +
                        "\n" +
                        "13h00: Xe và hướng dẫn viên đưa du khách đi tham quan Dinh thự Vua Mèo – Vương Chí Sình.\n" +
                        "\n" +
                        "Tiếp tục chuyến du lịch Hà Giang, đoàn đến thung lũng Sủng Là – nơi từng là bối cảnh bộ phim “Chuyện của Pao”. Tại đây, du khách ngắm nhìn cánh đồng hoa tam giác mạch trải dài hai bên đường và các sườn núi (nếu đến đúng mùa). Sau đó, đoàn trở về thị trấn Yên Minh.\n" +
                        "\n" +
                        "17h30: Đoàn dùng bữa tối, tự do khám phá thị trấn Yên Minh về đêm. Kết thúc ngày tham quan, du khách nghỉ ngơi tại khách sạn ở Yên Minh.\n" +
                        "\n" +
                        "Ngày 3: Yên Minh – Quản Bạ - Hà Nội\n" +
                        "6h30: Du khách dùng điểm tâm sáng, sau đó làm thủ tục trả phòng. Xe và hướng dẫn viên đưa đoàn về lại Hà Nội. Trên đường về, ghé tham quan và chụp ảnh tại cột mốc km số 0, tượng đài Bác Hồ và quảng trường thành phố.\n" +
                        "\n" +
                        "11h30: Dùng bữa trưa tại nhà hàng với các món đặc sản hấp dẫn của vùng núi phía Bắc. Đoàn tiếp tục hành trình về thủ đô. Đến trạm dừng chân, du khách tham quan và mua sắm các đặc sản vùng cao về làm quà cho gia đình, bạn bè.\n" +
                        "\n" +
                        "19h00: Đoàn tham quan về đến Hà Nội, kết thúc tour du lịch Hà Giang 3 ngày 2 đêm, hướng dẫn viên chia tay, chào tạm biệt và hẹn gặp lại du khách trong các tour du lịch Hà Giang hấp dẫn tiếp theo.", "Hà Nội", 3600000, getImageBytes(context, R.drawable.tn5)));


        return productList;
    }

    private static byte[] getImageBytes(Context context, int resourceId) {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) context.getResources().getDrawable(resourceId);  // Corrected line
        if (bitmapDrawable != null) {
            Bitmap bitmap = bitmapDrawable.getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            return stream.toByteArray();
        } else {
            return new byte[0];
        }
    }

}
