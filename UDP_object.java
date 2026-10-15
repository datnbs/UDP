//UDP OBJECT: ĐỀ ĐẦY ĐỦ + CODE CHUẨN (5 bài, cổng 2209)
//Cổng 2209. Server gửi 8 byte requestId + đối tượng serialize. Client sửa rồi gửi lại cùng cấu trúc.
//NetBeans: tạo package UDP (chữ hoa), class đặt trong đó, file Main0X.java cùng cấp với package.
//KHUNG CHUNG
//import java.util.*;
//import java.net.*;
//import java.io.*;
//import UDP.Lop;
//
//public class Main0X {
//    public static void main(String[] args) throws Exception {
//        // 1. Chuẩn bị + gửi phiếu xin đề
//        DatagramSocket socket = new DatagramSocket();
//        InetAddress sA = InetAddress.getByName("36.50.135.242");
//        int port = 2209;
//        String code = ";B23DCCN926;qCode";
//        socket.send(new DatagramPacket(code.getBytes(), code.length(), sA, port));
//
//        // 2. Nhận: 8 byte requestId + đối tượng
//        DatagramPacket nhan = new DatagramPacket(new byte[4096], 4096);
//        socket.receive(nhan);
//        String requestId = new String(nhan.getData(), 0, 8);
//        Lop o = (Lop) new ObjectInputStream(
//            new ByteArrayInputStream(nhan.getData(), 8, nhan.getLength() - 8)).readObject();
//
//        // 3. Sửa thuộc tính của o ...
//
//        // 4. Gửi: 8 byte requestId + đối tượng
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        baos.write(requestId.getBytes());
//        new ObjectOutputStream(baos).writeObject(o);
//        socket.send(new DatagramPacket(baos.toByteArray(), baos.size(), sA, port));
//
//        socket.close();
//    }
//}
//Lưu ý quan trọng:
//- baos.write(requestId.getBytes()) PHẢI đứng TRƯỚC new ObjectOutputStream(baos)
//- Buffer nhận 4096 byte (1024 không đủ)
//- Class rút gọn: chỉ cần đúng serialVersionUID và đúng tên/kiểu thuộc tính, để public
//────────────────────────────────────────────────────────────
//BÀI C1. QUẢN LÝ SINH VIÊN  [qCode: 8VdcIxPQ]
//[Mã câu hỏi (qCode): 8VdcIxPQ]. Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2209. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản sau: Đối tượng trao đổi là thể hiện của lớp UDP.Student được mô tả:
//- Tên đầy đủ lớp: UDP.Student
//- Các thuộc tính: id String, code String, name String, email String
//- 02 Hàm khởi tạo: public Student(String id, String code, String name, String email) và public Student(String code)
//- Trường dữ liệu: private static final long serialVersionUID = 20171107
//Thực hiện:
//a. Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng ";studentCode;qCode". Ví dụ: ";B15DCCN001;EE29C059"
//b. Nhận thông điệp chứa: 08 byte đầu chứa chuỗi requestId, các byte còn lại chứa một đối tượng là thể hiện của lớp Student từ server. Trong đó, các thông tin được thiết lập gồm id và name.
//c. Yêu cầu:
//- Chuẩn hóa tên theo quy tắc: Chữ cái đầu tiên in hoa, các chữ cái còn lại in thường và gán lại thuộc tính name của đối tượng
//- Tạo email ptit.edu.vn từ tên người dùng bằng cách lấy tên và các chữ cái bắt đầu của họ và tên đệm. Ví dụ: nguyen van tuan nam -> namnvt@ptit.edu.vn. Gán giá trị này cho thuộc tính email của đối tượng nhận được
//- Gửi thông điệp chứa đối tượng xử lý ở bước c lên Server với cấu trúc: 08 byte đầu chứa chuỗi requestId và các byte còn lại chứa đối tượng Student đã được sửa đổi.
//d. Đóng socket và kết thúc chương trình.
//Lưu ý: Hệ thống còn có mã câu hỏi 8yXucQXb cùng đề này — chỉ đổi qCode.
//File 1: Student.java (package UDP)
//package UDP;
//import java.io.Serializable;
//
//public class Student implements Serializable {
//    private static final long serialVersionUID = 20171107;
//    public String id, code, name, email;
//}
//File 2: Main01.java
//import java.util.*;
//import java.net.*;
//import java.io.*;
//import UDP.Student;
//
//public class Main01 {
//    public static void main(String[] args) throws Exception {
//        DatagramSocket socket = new DatagramSocket();
//        InetAddress sA = InetAddress.getByName("36.50.135.242");
//        int port = 2209;
//        String code = ";B23DCCN926;8VdcIxPQ";
//        socket.send(new DatagramPacket(code.getBytes(), code.length(), sA, port));
//
//        DatagramPacket nhan = new DatagramPacket(new byte[4096], 4096);
//        socket.receive(nhan);
//        String requestId = new String(nhan.getData(), 0, 8);
//        Student st = (Student) new ObjectInputStream(
//            new ByteArrayInputStream(nhan.getData(), 8, nhan.getLength() - 8)).readObject();
//
//        String[] w = st.name.trim().split("\\s+");
//        String ten = "", email = w[w.length - 1].toLowerCase();
//        for (int i = 0; i < w.length; i++) {
//            ten += w[i].substring(0, 1).toUpperCase() + w[i].substring(1).toLowerCase() + " ";
//            if (i < w.length - 1) email += Character.toLowerCase(w[i].charAt(0));
//        }
//        st.name = ten.trim();
//        st.email = email + "@ptit.edu.vn";
//
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        baos.write(requestId.getBytes());
//        new ObjectOutputStream(baos).writeObject(st);
//        socket.send(new DatagramPacket(baos.toByteArray(), baos.size(), sA, port));
//
//        socket.close();
//    }
//}
//Nếu WA: kiểm tra trim() trước split, và baos.write() trước ObjectOutputStream.
//────────────────────────────────────────────────────────────
//BÀI C2. KHÁCH HÀNG  [qCode: 8nxPat2M]
//[Mã câu hỏi (qCode): 8nxPat2M]. Thông tin khách hàng được yêu cầu thay đổi định dạng lại cho phù hợp với khu vực, cụ thể:
//a. Tên khách hàng cần được chuẩn hóa theo định dạng mới. Ví dụ: nguyen van hai duong -> DUONG, Nguyen Van Hai
//b. Ngày sinh của khách hàng đang ở dạng mm-dd-yyyy, cần được chuyển thành định dạng dd/mm/yyyy. Ví dụ: 10-11-2012 -> 11/10/2012
//c. Tài khoản khách hàng được tạo từ các chữ cái in thường được sinh tự động từ họ tên khách hàng. Ví dụ: nguyen van hai duong -> nvhduong
//Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2209. Yêu cầu là xây dựng một chương trình client giao tiếp với server theo mô tả sau:
//a. Đối tượng trao đổi là thể hiện của lớp UDP.Customer được mô tả như sau
//- Tên đầy đủ của lớp: UDP.Customer
//- Các thuộc tính: id String, code String, name String, dayOfBirth String, userName String
//- Một Hàm khởi tạo với đầy đủ các thuộc tính được liệt kê ở trên
//- Trường dữ liệu: private static final long serialVersionUID = 20151107;
//b. Client giao tiếp với server theo các bước
//- Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng ";studentCode;qCode". Ví dụ: ";B15DCCN001;EE29C059"
//- Nhận thông điệp chứa: 08 byte đầu chứa chuỗi requestId, các byte còn lại chứa một đối tượng là thể hiện của lớp Customer từ server. Trong đó, các thuộc tính id, code, name, dayOfBirth đã được thiết lập sẵn.
//- Yêu cầu thay đổi thông tin các thuộc tính như yêu cầu ở trên và gửi lại đối tượng khách hàng đã được sửa đổi lên server với cấu trúc: 08 byte đầu chứa chuỗi requestId và các byte còn lại chứa đối tượng Customer đã được sửa đổi.
//- Đóng socket và kết thúc chương trình.
//File 1: Customer.java (package UDP)
//package UDP;
//import java.io.Serializable;
//
//public class Customer implements Serializable {
//    private static final long serialVersionUID = 20151107;
//    public String id, code, name, dayOfBirth, userName;
//}
//File 2: Main02.java
//import java.util.*;
//import java.net.*;
//import java.io.*;
//import UDP.Customer;
//
//public class Main02 {
//    public static void main(String[] args) throws Exception {
//        DatagramSocket socket = new DatagramSocket();
//        InetAddress sA = InetAddress.getByName("36.50.135.242");
//        int port = 2209;
//        String code = ";B23DCCN926;8nxPat2M";
//        socket.send(new DatagramPacket(code.getBytes(), code.length(), sA, port));
//
//        DatagramPacket nhan = new DatagramPacket(new byte[4096], 4096);
//        socket.receive(nhan);
//        String requestId = new String(nhan.getData(), 0, 8);
//        Customer c = (Customer) new ObjectInputStream(
//            new ByteArrayInputStream(nhan.getData(), 8, nhan.getLength() - 8)).readObject();
//
//        String[] w = c.name.trim().split("\\s+");
//        int n = w.length;
//        String ten = "", user = "";
//        for (int i = 0; i < n - 1; i++) {
//            ten += w[i].substring(0, 1).toUpperCase() + w[i].substring(1).toLowerCase() + " ";
//            user += Character.toLowerCase(w[i].charAt(0));
//        }
//        c.name = (w[n - 1].toUpperCase() + ", " + ten).trim();
//        c.userName = user + w[n - 1].toLowerCase();
//        String[] d = c.dayOfBirth.split("-");
//        c.dayOfBirth = d[1] + "/" + d[0] + "/" + d[2];
//
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        baos.write(requestId.getBytes());
//        new ObjectOutputStream(baos).writeObject(c);
//        socket.send(new DatagramPacket(baos.toByteArray(), baos.size(), sA, port));
//
//        socket.close();
//    }
//}
//Chú ý: ngày d[1]/d[0]/d[2] (đổi chỗ ngày và tháng).
//────────────────────────────────────────────────────────────
//BÀI C3. SẢN PHẨM  [qCode: kZqFKEDL]
//[Mã câu hỏi (qCode): kZqFKEDL]. Thông tin sản phẩm vì một lý do nào đó đã bị sửa đổi thành không đúng, cụ thể:
//a. Tên sản phẩm bị đổi ngược từ đầu tiên và từ cuối cùng, ví dụ: "lenovo thinkpad T520" bị chuyển thành "T520 thinkpad lenovo"
//b. Số lượng sản phẩm cũng bị đảo ngược giá trị, ví dụ từ 9981 thành 1899
//Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2209. Yêu cầu là xây dựng một chương trình client giao tiếp với server để gửi/nhận các sản phẩm theo mô tả dưới đây:
//a. Đối tượng trao đổi là thể hiện của lớp Product được mô tả như sau
//- Tên đầy đủ của lớp: UDP.Product
//- Các thuộc tính: id String, code String, name String, quantity int
//- Một hàm khởi tạo có đầy đủ các thuộc tính được liệt kê ở trên
//- Trường dữ liệu: private static final long serialVersionUID = 20161107;
//b. Giao tiếp với server theo kịch bản
//- Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng ";studentCode;qCode". Ví dụ: ";B15DCCN001;EE29C059"
//- Nhận thông điệp chứa: 08 byte đầu chứa chuỗi requestId, các byte còn lại chứa một đối tượng là thể hiện của lớp Product từ server. Trong đối tượng này, các thuộc tính id, name và quantity đã được thiết lập giá trị.
//- Sửa các thông tin sai của đối tượng về tên và số lượng như mô tả ở trên và gửi đối tượng vừa được sửa đổi lên server theo cấu trúc: 08 byte đầu chứa chuỗi requestId và các byte còn lại chứa đối tượng Product đã được sửa đổi.
//- Đóng socket và kết thúc chương trình.
//File 1: Product.java (package UDP)
//package UDP;
//import java.io.Serializable;
//
//public class Product implements Serializable {
//    private static final long serialVersionUID = 20161107;
//    public String id, code, name;
//    public int quantity;
//}
//File 2: Main03.java
//import java.util.*;
//import java.net.*;
//import java.io.*;
//import UDP.Product;
//
//public class Main03 {
//    public static void main(String[] args) throws Exception {
//        DatagramSocket socket = new DatagramSocket();
//        InetAddress sA = InetAddress.getByName("36.50.135.242");
//        int port = 2209;
//        String code = ";B23DCCN926;kZqFKEDL";
//        socket.send(new DatagramPacket(code.getBytes(), code.length(), sA, port));
//
//        DatagramPacket nhan = new DatagramPacket(new byte[4096], 4096);
//        socket.receive(nhan);
//        String requestId = new String(nhan.getData(), 0, 8);
//        Product p = (Product) new ObjectInputStream(
//            new ByteArrayInputStream(nhan.getData(), 8, nhan.getLength() - 8)).readObject();
//
//        String[] w = p.name.trim().split("\\s+");
//        String t = w[0];
//        w[0] = w[w.length - 1];
//        w[w.length - 1] = t;
//        p.name = String.join(" ", w);
//        p.quantity = Integer.parseInt(new StringBuilder(p.quantity + "").reverse().toString());
//
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        baos.write(requestId.getBytes());
//        new ObjectOutputStream(baos).writeObject(p);
//        socket.send(new DatagramPacket(baos.toByteArray(), baos.size(), sA, port));
//
//        socket.close();
//    }
//}
//────────────────────────────────────────────────────────────
//BÀI C4. CHUẨN HOÁ THÔNG TIN SÁCH  [qCode: LFACr5Bi]
//[Mã câu hỏi (qCode): LFACr5Bi]. Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2209. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản sau: Đối tượng trao đổi là thể hiện của lớp UDP.Book được mô tả:
//- Tên đầy đủ lớp: UDP.Book
//- Các thuộc tính: id (String), title (String), author (String), isbn (String), publishDate (String)
//- Hàm khởi tạo: public Book(String id, String title, String author, String isbn, String publishDate)
//- Trường dữ liệu: private static final long serialVersionUID = 20251107L
//Thực hiện:
//a. Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng ";studentCode;qCode". Ví dụ: ";B23DCCN005;eQkvAeId"
//b. Nhận thông điệp chứa: 08 byte đầu chứa chuỗi requestId, các byte còn lại chứa một đối tượng là thể hiện của lớp Book từ server. Trong đó, các thuộc tính id, title, author, isbn, và publishDate đã được thiết lập sẵn.
//c. Thực hiện:
//1. Chuẩn hóa title: Với mỗi từ, viết hoa chữ cái đầu tiên, viết thường các chữ cái còn lại.
//2. Chuẩn hóa author theo định dạng "Họ, Tên": Họ là từ đầu tiên, viết hoa tất cả; Tên là các từ còn lại, mỗi từ viết hoa chữ cái đầu tiên, viết thường các chữ cái còn lại, giữa mỗi từ chỉ có đúng một dấu cách.
//3. Chuẩn hóa mã ISBN theo định dạng "978-3-16-148410-0"
//4. Chuyển đổi publishDate từ yyyy-mm-dd sang mm/yyyy.
//d. Gửi lại đối tượng đã được chuẩn hóa về server với cấu trúc: 08 byte đầu chứa chuỗi requestId và các byte còn lại chứa đối tượng Book đã được sửa đổi.
//e. Đóng socket và kết thúc chương trình.
//Test ví dụ: Input title "wAsSegd NCAxhCY" → Output "Wassegd Ncaxhcy". Input author "cFPISv tlpiAvv TzfHf" → Output "CFPISV, Tlpiavv Tzfhf". ISBN 13 chữ số → xxx-x-xx-xxxxxx-x.
//Lưu ý: Hệ thống còn có mã câu hỏi qAuSMw4o cùng đề này — chỉ đổi qCode.
//File 1: Book.java (package UDP)
//package UDP;
//import java.io.Serializable;
//
//public class Book implements Serializable {
//    private static final long serialVersionUID = 20251107L;
//    public String id, title, author, isbn, publishDate;
//}
//File 2: Main04.java
//import java.util.*;
//import java.net.*;
//import java.io.*;
//import UDP.Book;
//
//public class Main04 {
//    public static void main(String[] args) throws Exception {
//        DatagramSocket socket = new DatagramSocket();
//        InetAddress sA = InetAddress.getByName("36.50.135.242");
//        int port = 2209;
//        String code = ";B23DCCN926;LFACr5Bi";
//        socket.send(new DatagramPacket(code.getBytes(), code.length(), sA, port));
//
//        DatagramPacket nhan = new DatagramPacket(new byte[4096], 4096);
//        socket.receive(nhan);
//        String requestId = new String(nhan.getData(), 0, 8);
//        Book b = (Book) new ObjectInputStream(
//            new ByteArrayInputStream(nhan.getData(), 8, nhan.getLength() - 8)).readObject();
//
//        String[] t = b.title.trim().split("\\s+");
//        String[] a = b.author.trim().split("\\s+");
//        String title = "", ten = "";
//        for (String x : t) title += x.substring(0, 1).toUpperCase() + x.substring(1).toLowerCase() + " ";
//        for (int i = 1; i < a.length; i++) ten += a[i].substring(0, 1).toUpperCase() + a[i].substring(1).toLowerCase() + " ";
//        b.title = title.trim();
//        b.author = (a[0].toUpperCase() + ", " + ten).trim();
//        String s = b.isbn;
//        b.isbn = s.substring(0, 3) + "-" + s.substring(3, 4) + "-" + s.substring(4, 6) + "-" + s.substring(6, 12) + "-" + s.substring(12);
//        String[] d = b.publishDate.trim().split("[-\\s]+");
//        b.publishDate = d[1] + "/" + d[0];
//
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        baos.write(requestId.getBytes());
//        new ObjectOutputStream(baos).writeObject(b);
//        socket.send(new DatagramPacket(baos.toByteArray(), baos.size(), sA, port));
//
//        socket.close();
//    }
//}
//Chú ý: isbn cắt theo substring(0,3), (3,4), (4,6), (6,12), (12).
//publishDate split bằng [-\\s]+ để xử lý khoảng trắng lạ.
//────────────────────────────────────────────────────────────
//BÀI C5. QUẢN LÝ THÔNG TIN NHÂN VIÊN  [qCode: ySsumsIE]
//[Mã câu hỏi (qCode): ySsumsIE]. Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2209. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản sau: Đối tượng trao đổi là thể hiện của lớp UDP.Employee được mô tả:
//- Tên đầy đủ lớp: UDP.Employee
//- Các thuộc tính: id (String), name (String), salary (double), hireDate (String)
//- Hàm khởi tạo: public Employee(String id, String name, double salary, String hireDate)
//- Trường dữ liệu: private static final long serialVersionUID = 20261107L
//Thực hiện:
//a. Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng ";studentCode;qCode". Ví dụ: ";B23DCCN006;ITleSdqV"
//b. Nhận thông điệp chứa: 08 byte đầu chứa chuỗi requestId, các byte còn lại chứa một đối tượng là thể hiện của lớp Employee từ server. Trong đó, các thuộc tính id, name, salary và hireDate đã được thiết lập sẵn.
//c. Thực hiện:
//- Chuẩn hóa name: viết hoa chữ cái đầu của mỗi từ, ví dụ "john doe" thành "John Doe".
//- Tăng salary: tăng x% lương, với x bằng tổng các chữ số của năm sinh.
//- Chuyển đổi hireDate từ định dạng yyyy-mm-dd sang dd/mm/yyyy. Ví dụ: "2023-07-15" thành "15/07/2023".
//- Gửi lại đối tượng đã được chuẩn hóa về server với cấu trúc: 08 byte đầu chứa chuỗi requestId và các byte còn lại chứa đối tượng Employee đã được sửa đổi.
//d. Đóng socket và kết thúc chương trình.
//File 1: Employee.java (package UDP)
//package UDP;
//import java.io.Serializable;
//
//public class Employee implements Serializable {
//    private static final long serialVersionUID = 20261107L;
//    public String id, name, hireDate;
//    public double salary;
//}
//File 2: Main05.java
//import java.util.*;
//import java.net.*;
//import java.io.*;
//import UDP.Employee;
//
//public class Main05 {
//    public static void main(String[] args) throws Exception {
//        DatagramSocket socket = new DatagramSocket();
//        InetAddress sA = InetAddress.getByName("36.50.135.242");
//        int port = 2209;
//        String code = ";B23DCCN926;ySsumsIE";
//        socket.send(new DatagramPacket(code.getBytes(), code.length(), sA, port));
//
//        DatagramPacket nhan = new DatagramPacket(new byte[4096], 4096);
//        socket.receive(nhan);
//        String requestId = new String(nhan.getData(), 0, 8);
//        Employee e = (Employee) new ObjectInputStream(
//            new ByteArrayInputStream(nhan.getData(), 8, nhan.getLength() - 8)).readObject();
//
//        String[] t = e.name.trim().split("\\s+");
//        String ten = "";
//        for (String x : t) ten += x.substring(0, 1).toUpperCase() + x.substring(1).toLowerCase() + " ";
//        e.name = ten.trim();
//        String[] d = e.hireDate.trim().split("-");
//        e.hireDate = d[2] + "/" + d[1] + "/" + d[0];
//        int tong = 0;
//        for (char c : d[0].toCharArray()) tong += c - '0';
//        e.salary = e.salary * (1 + tong / 100.0);
//
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        baos.write(requestId.getBytes());
//        new ObjectOutputStream(baos).writeObject(e);
//        socket.send(new DatagramPacket(baos.toByteArray(), baos.size(), sA, port));
//
//        socket.close();
//    }
//}
//Chú ý: chia 100.0 (không phải 100) để tránh integer division. Lấy năm từ hireDate (d[0]) để tính tổng chữ số.
