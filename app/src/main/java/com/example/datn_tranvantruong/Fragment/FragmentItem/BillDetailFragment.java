package com.example.datn_tranvantruong.Fragment.FragmentItem;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.datn_tranvantruong.Activity.Intro_Activity;
import com.example.datn_tranvantruong.DBHandler.BillHandler;
import com.example.datn_tranvantruong.DBHandler.CustomerHandler;
import com.example.datn_tranvantruong.DBHandler.ProductHandler;
import com.example.datn_tranvantruong.Model.BillStatistic;
import com.example.datn_tranvantruong.Model.Product;
import com.example.datn_tranvantruong.R;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.element.Paragraph;

import org.w3c.dom.Document;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.NumberFormat;

public class BillDetailFragment extends Fragment {
    TextView bill_id;
    TextView users_name;
    TextView users_address;
    TextView users_phone;
    TextView users_email;
    TextView product_id;
    TextView product_name;
    TextView quatity;
    TextView price;
    TextView bill_date;
    TextView bill_price;
    ImageView createPdfButton;
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bill_detail, container, false);

        bill_id = view.findViewById(R.id.bill_id);
        users_name = view.findViewById(R.id.users_name);
        users_address = view.findViewById(R.id.users_address);
        users_phone = view.findViewById(R.id.users_phone);
        users_email = view.findViewById(R.id.users_email);
        product_id = view.findViewById(R.id.product_id);
        product_name = view.findViewById(R.id.product_name);
        quatity = view.findViewById(R.id.product_quatity);
        price = view.findViewById(R.id.product_price);
        bill_date = view.findViewById(R.id.bill_date);
        bill_price = view.findViewById(R.id.bill_price);
        ProductHandler productHandler = new ProductHandler();
        CustomerHandler customerHandler = new CustomerHandler();
        BillHandler billHandler = new BillHandler();
        createPdfButton = view.findViewById(R.id.crtPDF);
        createPdfButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kiểm tra quyền
                if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    // Nếu quyền chưa được cấp, yêu cầu người dùng cấp quyền
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
                } else {
                    // Nếu đã có quyền, thực hiện tạo PDF
                    createPdf();
                }
            }
        });
        Bundle bundle = getArguments();
        if (bundle != null) {
            String billdate = bundle.getString("billdate");
            String billid = bundle.getString("billid");
            String billPrice = bundle.getString("billprice");
            int productId = bundle.getInt("productId");



            users_email.setText(billHandler.getBillInfo(Integer.parseInt(billid)).getUserEmail());
            users_name.setText(billHandler.getBillInfo(Integer.parseInt(billid)).getUserName());
            users_address.setText(billHandler.getBillInfo(Integer.parseInt(billid)).getUserAddress());
            users_phone.setText(billHandler.getBillInfo(Integer.parseInt(billid)).getUserPhone());

            // Lấy thông tin hóa đơn
            bill_id.setText("Mã số: #KTTRAVEL" + billid);
            price.setText(billPrice + "VND");
            bill_date.setText(billdate);
            product_id.setText(String.valueOf(productId));


            product_name.setText(productHandler.getProductNameById(productId));

            quatity.setText(billHandler.getBillQuantityById(Integer.parseInt(billid)));

            // Chuyển đổi giá từ dạng số sang dạng chữ và hiển thị trong TextView bill_price
            String priceInWords = convertNumberToWords(Integer.parseInt(billPrice));
            bill_price.setText(priceInWords + " việt nam đồng.");
        }
        return view;
    }

    private String convertNumberToWords(int number) {
        // Sử dụng đoạn mã chuyển đổi số thành chữ ở đây
        String[] units = {"", " nghìn ", " triệu ", " tỷ "};
        String result = "";

        int unitIndex = 0;

        do {
            int num = number % 1000;
            if (num > 0) {
                String str = convertLessThanOneThousand(num);
                result = str + units[unitIndex] + " " + result;
            }
            unitIndex++;
            number /= 1000;
        } while (number > 0);

        return result.trim();
    }

    private String convertLessThanOneThousand(int number) {
        String[] tensNames = {"", "mười", "hai mươi", "ba mươi", "bốn mươi", "năm mươi", "sáu mươi", "bảy mươi", "tám mươi", "chín mươi"};
        String[] numNames = {"", "một", "hai", "ba", "bốn", "năm", "sáu", "bảy", "tám", "chín", "mười", "mười một", "mười hai", "mười ba", "mười bốn", "mười lăm", "mười sáu", "mười bảy", "mười tám", "mười chín"};

        String result;

        if (number % 100 < 20) {
            result = numNames[number % 100];
            number /= 100;
        } else {
            result = numNames[number % 10];
            number /= 10;
            result = tensNames[number % 10] + " " + result;
            number /= 10;
        }

        if (number > 0) {
            result = numNames[number] + " trăm " + result;
        }

        return result;
    }
    private void createPdf() {
        // Tạo tệp PDF tại thư mục Downloads
        String pdfFileName = "bill_detail.pdf";
        File pdfDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        String pdfFilePath = pdfDirectory + "/" + pdfFileName;
        File file = new File(pdfFilePath);

        try {
            // Tạo một đối tượng PdfWriter để ghi vào tệp
            PdfWriter writer = new PdfWriter(new FileOutputStream(file));

            // Tạo một đối tượng PdfDocument để thêm nội dung
            PdfDocument pdfDocument = new PdfDocument(writer);

            // Tạo một đối tượng Document để thêm các phần tử
            com.itextpdf.layout.Document document = new com.itextpdf.layout.Document(pdfDocument);

            // Thêm nội dung của Fragment vào tệp PDF
            document.add(new Paragraph("Bill Detail"));

            // Thêm các thông tin từ Fragment vào tệp PDF
            document.add(new Paragraph("User Name: " + users_name.getText()));
            document.add(new Paragraph("User Address: " + users_address.getText()));
            document.add(new Paragraph("User Phone: " + users_phone.getText()));
            document.add(new Paragraph("User Email: " + users_email.getText()));
            document.add(new Paragraph("Product Name: " + product_name.getText()));
            document.add(new Paragraph("Product Quantity: " + quatity.getText()));
            document.add(new Paragraph("Product Price: " + price.getText()));
            document.add(new Paragraph("Bill Date: " + bill_date.getText()));
            document.add(new Paragraph("Bill Price: " + bill_price.getText()));

            // Đóng tất cả tài nguyên
            document.close();

            // Hiển thị thông báo hoặc cập nhật UI nếu cần thiết

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
