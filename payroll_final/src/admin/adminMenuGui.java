package admin;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JDateChooser;

import Main.ConnectDB;
import Main.EmployeeName;
import Main.LoginGui;
import net.proteanit.sql.DbUtils;

import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import org.jdesktop.xswingx.JXSearchField;
import org.jdesktop.xswingx.JXSearchField.LayoutStyle;
import javax.swing.JScrollPane;
import java.awt.ScrollPane;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.event.CaretListener;
import javax.swing.event.CaretEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class adminMenuGui extends JFrame {

	private JPanel contentPane;


	static Connection conn;
	static Statement stmt;
	static ResultSet rs;
	private JTextField textField_username;
	private JTextField textField_password;
	private JTextField textField_firstname;
	private JTextField textField_lastname;
	private JTextField textField_age;
	private JTextField textField_city;
	private JTextField textField_email;
	private JTextField textField_Mobile;
	private JTextField textField_emptype;
	private JTextField textField_stat;

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+"[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static final String MOBILE_PATTERN = "\\d{10}";
	private JTextField textField_Username;
	private JTextField textField_Password;
	private JTextField textField_Firstname;
	private JTextField textField_Lastname;
	private JTextField textField_Age;
	private JTextField textField_City;
	private JTextField textField_Email;
	private JTextField textField_mobil;
	private JTextField textField_Empt1;
	private JTextField textField_Stat1;

	private JTable table;
	private JTextField textField_jobcode;
	private JTextField textField_jobtitle;
	private JTextField textField_salaray;
	private JTable table1;
	private JTextField textField_id;
	float hours;

	JLabel Time_O = new JLabel(dtf.format(lt));
	JButton btnTimeOut;
	JLabel Time_I = new JLabel(dtf.format(lt));
	String timein = Time_I.getText();
	String timeout = Time_O.getText();

	DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	LocalDateTime now = LocalDateTime.now();
	DateTimeFormatter dtfAP = DateTimeFormatter.ofPattern("a");
	LocalTime ltAP = LocalTime.now();
	DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("MM");
	LocalDateTime now2 = LocalDateTime.now();


	static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("h:m a");
	static LocalTime lt = LocalTime.now();
	private JTextField textField_jobIdchange;
	private JTextField textField_lnjobdits;
	private JTextField textField_fnjobdits;
	private JTextField textField_jobID;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					adminMenuGui adminGUiframe = new adminMenuGui();
					adminGUiframe.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//	 * Create the frame

	public adminMenuGui() {
		setTitle("Admin Menu");
		setResizable(false);
		conn = ConnectDB.doConnect();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 774, 553);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 40, 738, 465);
		contentPane.add(tabbedPane);

		JPanel panel_home = new JPanel();
		panel_home.setToolTipText("");
		tabbedPane.addTab("Home", null, panel_home, null);
		panel_home.setLayout(null);

		Time_I.setBackground(Color.WHITE);
		Time_I.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		Time_I.setForeground(Color.RED);
		Time_I.setBounds(12, 80, 120, 20);
		panel_home.add(Time_I);

		btnTimeOut = new JButton("TIME OUT");
		btnTimeOut.setEnabled(false);
		btnTimeOut.setBounds(144, 112, 101, 23);
		panel_home.add(btnTimeOut);

		JButton btnTimeI = new JButton("TIME IN");
		btnTimeI.setBounds(144, 79, 101, 23);
		panel_home.add(btnTimeI);
		btnTimeI.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {

				if(!btnTimeI.isEnabled()) {
					System.out.println("enable line 186");
					btnTimeOut.setEnabled(true);
					Time_O.setEnabled(true);
				}
			}
		});
		btnTimeI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnTimeI.setEnabled(false);
				Time_I.setEnabled(false);

				try {

					Connection	conn = ConnectDB.doConnect();

					CallableStatement statement = conn.prepareCall("CALL insertTI(?,?,?,?)");
					statement.setString(1, String.valueOf(EmployeeName.empid).toString());
					statement.setString(2, dtf1.format(now));
					statement.setString(3, timein);
					statement.setString(4, dtf2.format(now2));

					statement.executeQuery();

					btnTimeI.setEnabled(false);

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 

			}
		});

		JLabel label_empId = new JLabel("null");
		label_empId.setForeground(Color.RED);
		label_empId.setText(String.valueOf(EmployeeName.empid).toString());
		label_empId.setBounds(125, 13, 120, 14);
		panel_home.add(label_empId);
		label_empId.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				String empId = label_empId.getText();
				String datenow = dtf1.format(now);

				try {
					Connection conn = ConnectDB.doConnect();

					PreparedStatement pst = null;
					pst = conn.prepareStatement("select * from attendance where employee_id = ? and date = ?");
					pst.setString(1, empId);
					pst.setString(2, datenow);
					ResultSet rs = pst.executeQuery();
//deleted
					while(rs.next()) {
						if(rs.getString(3) != null) {
							btnTimeI.setEnabled(false);
							Time_I.setEnabled(false);
							Time_I.setText(rs.getString(3));
							System.out.println("timeinverify");
						}

						if(rs.getString(4) != null) {
							System.out.println("disable line 248");
							btnTimeOut.setEnabled(false);
							Time_O.setEnabled(false);
							Time_O.setText(rs.getString(4));
							System.out.println("timoutverify");
						}
					}

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


			}
		});

		Time_O.setEnabled(false);			
		Time_O.setBackground(Color.WHITE);
		Time_O.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		Time_O.setForeground(Color.RED);
		Time_O.setBounds(12, 113, 120, 20);
		panel_home.add(Time_O);
		Time_O.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if(!btnTimeI.isEnabled()) {

					Thread clock = new Thread() {
						public void run() {

							try {
								for(;;) {
									Calendar calz = new GregorianCalendar();

									int minute = calz.get(Calendar.MINUTE);
									int hour = calz.get(Calendar.HOUR);
									String meridiem = calz.getDisplayName(Calendar.AM_PM, Calendar.SHORT, Locale.getDefault());


									Time_O.setText(hour + ":" + minute  + " "+ meridiem); //
									sleep(1000);
								}
							} catch(InterruptedException e){
								e.printStackTrace();
							}
						}
					};
					clock.start();

					Time_O.setEnabled(true);
					System.out.println("enable line 397");
					//btnTimeOut.setEnabled(true);
				}

			}
		});
		btnTimeOut.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				System.out.println("disable line 406");
				btnTimeOut.setEnabled(false);

			}
		});


		btnTimeOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Time_O.getText();
				String eId = label_empId.getText();
				//String ngayon = dtf1.format(now);

				try {
					Connection conn = ConnectDB.doConnect();

					PreparedStatement pst = null;
					pst = conn.prepareStatement("select * from attendance where date = curdate() and employee_id = " + eId);

					ResultSet rs = pst.executeQuery();

					while(rs.next()) {
						String ti = rs.getString(3);
						String to = Time_O.getText();
						DateFormat sdf = new SimpleDateFormat("hh:mm aa");
						Date d1 = sdf.parse(ti);
						Date d2 = sdf.parse(to);

						if(d1.after(d2)){
							float diffMs = d1.getTime() - d2.getTime();
							float diffSec = diffMs / 1000;

							float min = diffSec / 60;
							hours = (min /60)-1;

							System.out.println("After: Difference in Hours "+hours);

						}
						if(d1.before(d2)){
							float diffMs = d2.getTime() - d1.getTime();
							float diffSec = diffMs / 1000;


							float min = diffSec / 60;
							hours = (min /60)-1;

							System.out.println("before: Difference in Hours "+hours);

						}

						if(d1.equals(d2)){
							System.out.println("The difference is 0 minutes and 0 seconds.");
						}

						System.out.println("computed");

						try {

							Connection conn1 = ConnectDB.doConnect();

							PreparedStatement pstt = null;
							pstt = conn1.prepareStatement("UPDATE attendance SET time_out=?, totalhr = ?  WHERE employee_id=? and date = curdate()");
							System.out.println("nagupdate na");
							pstt.setString(1, to);
							pstt.setFloat(2, hours);
							pstt.setString(3, eId);
							pstt.executeUpdate();
							//ResultSet rss = pstt.executeQuery();
							System.out.println("disable line 474");
							btnTimeOut.setEnabled(false);
							Time_O.setEnabled(false);

							JOptionPane.showMessageDialog(null, "Successfully added");

						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();	
						}

						break;
					}

				}catch(SQLException | ParseException c) {
					c.printStackTrace();
				}
				System.out.println("disable line 491");

			}	
		});

		JLabel label_Emp = new JLabel("Employee #:");
		label_Emp.setBounds(12, 13, 101, 14);
		panel_home.add(label_Emp);

		JLabel lblTimeManagement = new JLabel("Time Management");
		lblTimeManagement.setBounds(12, 51, 158, 16);
		panel_home.add(lblTimeManagement);

		JPanel panel = new JPanel();
		tabbedPane.addTab("Employee Settings", null, panel, null);
		panel.setLayout(null);

		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_1.setBounds(10, 11, 713, 399);
		panel.add(tabbedPane_1);

		JPanel panel_addEmp = new JPanel();
		tabbedPane_1.addTab("Add Employee", null, panel_addEmp, null);
		panel_addEmp.setLayout(null);

		JLabel label = new JLabel("Username: ");
		label.setBounds(10, 56, 118, 20);
		panel_addEmp.add(label);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 37, 688, 2);
		panel_addEmp.add(separator);

		JLabel label_1 = new JLabel("Password: ");
		label_1.setBounds(10, 87, 118, 20);
		panel_addEmp.add(label_1);

		JLabel label_2 = new JLabel("Firstname: ");
		label_2.setBounds(10, 118, 118, 20);
		panel_addEmp.add(label_2);

		JLabel label_3 = new JLabel("Lastname: ");
		label_3.setBounds(10, 149, 118, 20);
		panel_addEmp.add(label_3);

		JLabel label_4 = new JLabel("Date of Birth:");
		label_4.setBounds(10, 180, 118, 20);
		panel_addEmp.add(label_4);

		JLabel label_5 = new JLabel("Age: ");
		label_5.setBounds(10, 208, 69, 20);
		panel_addEmp.add(label_5);

		textField_username = new JTextField();
		textField_username.setColumns(10);
		textField_username.setBounds(138, 53, 146, 26);
		panel_addEmp.add(textField_username);

		textField_password = new JTextField();
		textField_password.setColumns(10);
		textField_password.setBounds(138, 81, 146, 26);
		panel_addEmp.add(textField_password);

		textField_firstname = new JTextField();
		textField_firstname.setColumns(10);
		textField_firstname.setBounds(138, 112, 146, 26);
		panel_addEmp.add(textField_firstname);

		textField_lastname = new JTextField();
		textField_lastname.setColumns(10);
		textField_lastname.setBounds(138, 143, 146, 26);
		panel_addEmp.add(textField_lastname);

		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(138, 174, 146, 26);
		panel_addEmp.add(dateChooser);

		textField_age = new JTextField();
		textField_age.setColumns(10);
		textField_age.setBounds(138, 205, 146, 26);
		panel_addEmp.add(textField_age);

		JLabel label_6 = new JLabel("Gender: ");
		label_6.setBounds(10, 241, 118, 20);
		panel_addEmp.add(label_6);

		JRadioButton rdbtnMale = new JRadioButton("male");
		rdbtnMale.setBounds(138, 238, 146, 20);
		panel_addEmp.add(rdbtnMale);

		JRadioButton rdbtnFemale = new JRadioButton("female");
		rdbtnFemale.setBounds(138, 261, 155, 20);
		panel_addEmp.add(rdbtnFemale);

		ButtonGroup bG = new ButtonGroup();
		bG.add(rdbtnMale);
		bG.add(rdbtnFemale);

		JLabel label_7 = new JLabel("Country: ");
		label_7.setBounds(10, 284, 118, 20);
		panel_addEmp.add(label_7);

		String[] c= new String[]{"US", "PH", "CHINA"};
		JComboBox comboBox = new JComboBox(c);
		comboBox.setBounds(138, 284, 146, 26);
		panel_addEmp.add(comboBox);


		JLabel label_8 = new JLabel("Address: ");
		label_8.setBounds(383, 56, 103, 20);
		panel_addEmp.add(label_8);

		JTextArea textArea = new JTextArea();
		textArea.setBounds(443, 54, 235, 56);
		panel_addEmp.add(textArea);

		textField_city = new JTextField();
		textField_city.setColumns(10);
		textField_city.setBounds(443, 118, 146, 26);
		panel_addEmp.add(textField_city);

		JLabel label_9 = new JLabel("City: ");
		label_9.setBounds(383, 121, 50, 20);
		panel_addEmp.add(label_9);

		JLabel label_10 = new JLabel("Email: ");
		label_10.setBounds(383, 152, 50, 20);
		panel_addEmp.add(label_10);

		textField_email = new JTextField();
		textField_email.setColumns(10);
		textField_email.setBounds(443, 149, 146, 26);
		panel_addEmp.add(textField_email);

		JLabel label_11 = new JLabel("Mobile: ");
		label_11.setBounds(383, 183, 50, 20);
		panel_addEmp.add(label_11);

		textField_Mobile = new JTextField();
		textField_Mobile.setColumns(10);
		textField_Mobile.setBounds(443, 180, 146, 26);
		panel_addEmp.add(textField_Mobile);

		JLabel label_12 = new JLabel("Employee type:");
		label_12.setBounds(363, 217, 100, 14);
		panel_addEmp.add(label_12);

		textField_emptype = new JTextField();
		textField_emptype.setColumns(10);
		textField_emptype.setBounds(473, 214, 35, 20);
		panel_addEmp.add(textField_emptype);

		JLabel label_13 = new JLabel("[1] Admin");
		label_13.setBounds(383, 241, 103, 14);
		panel_addEmp.add(label_13);

		JLabel label_14 = new JLabel("[2] Employee");
		label_14.setBounds(383, 264, 103, 14);
		panel_addEmp.add(label_14);

		JLabel label_15 = new JLabel("Employee Status:");
		label_15.setBounds(518, 217, 104, 14);
		panel_addEmp.add(label_15);

		textField_stat = new JTextField();
		textField_stat.setColumns(10);
		textField_stat.setBounds(632, 214, 35, 20);
		panel_addEmp.add(textField_stat);

		JLabel label_16 = new JLabel("[1] Active");
		label_16.setBounds(533, 244, 89, 14);
		panel_addEmp.add(label_16);

		JLabel label_17 = new JLabel("[2] Inactive");
		label_17.setBounds(533, 264, 89, 14);
		panel_addEmp.add(label_17);

		JLabel label_18 = new JLabel("[3] Deactivate");
		label_18.setBounds(533, 287, 89, 14);
		panel_addEmp.add(label_18);

		JButton button = new JButton("Register Employeee");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String fname = textField_firstname.getText();
				String lname = textField_lastname.getText();
				String Usern = textField_username.getText().toString();
				String Passw = textField_password.getText().toString();
				String City = textField_city.getText();
				String emil =  textField_email.getText().toString();
				String num = textField_Mobile.getText().toString();
				String Age = textField_age.getText().toString();
				java.sql.Date dat = new java.sql.Date(dateChooser.getDate().getTime());
				String country = comboBox.getSelectedItem().toString();
				String addres = textArea.getText();
				String gender  = bG.getSelection().getActionCommand();
				String empT = textField_emptype.getText().toString();
				String empStat = textField_stat.getText().toString();;

				boolean testEmail, testMob;
				Pattern patternEmail, patternMob;
				Matcher matcherEmail, matcherMob;


				patternEmail = Pattern.compile(EMAIL_PATTERN);
				matcherEmail = patternEmail.matcher(emil);
				testEmail =  matcherEmail.matches();

				patternMob = Pattern.compile(MOBILE_PATTERN);
				matcherMob = patternMob.matcher(num);
				testMob = matcherMob.matches();

				if(!testEmail){
					JOptionPane.showMessageDialog(null, "Enter Valid email");
				}
				if (!testMob) {
					JOptionPane.showMessageDialog(null, "Enter Valid mobile");

				}

				else{

					try {

						String sqladd = "insert into employee_Table(username, passwords, emp_type, status_id,last_name,first_name,dob,age,gender,country,city,address,email,mobile_no)"
								+"values('"+Usern+"','"+Passw+"','"+empT+"','"+empStat+"','"+lname+"','"+fname+"','"+dat+"','"+Age+"','"+gender+"','"+country+"','"+City+"','"+addres+"','"+emil+"','"+num+"')";

						if(testEmail && testMob){
							JOptionPane.showMessageDialog(null, "Are You Sure to add this Record?");
						}
						System.out.println(sqladd);
						Class.forName("com.mysql.jdbc.Driver");
						stmt = conn.prepareCall(sqladd);
						stmt.executeUpdate(sqladd);

						JOptionPane.showMessageDialog(null, "Record Added Successfully");
						new LoginGui();
					} catch (SQLException | ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null, e1);

					}
				}
			}
		});
		button.setBounds(264, 322, 169, 23);
		panel_addEmp.add(button);

		JSeparator separator_3 = new JSeparator();
		separator_3.setOrientation(SwingConstants.VERTICAL);
		separator_3.setBounds(341, 56, 12, 248);
		panel_addEmp.add(separator_3);
		ButtonGroup bG1 = new ButtonGroup();

		String[] cb= new String[]{"US", "PH", "CHINA"};




		JPanel panel_updtEmp = new JPanel();
		tabbedPane_1.addTab("Update Employee", null, panel_updtEmp, null);
		panel_updtEmp.setLayout(null);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 41, 688, 2);
		panel_updtEmp.add(separator_1);

		JLabel notfound = new JLabel("");
		notfound.setBounds(144, 13, 140, 14);
		panel_updtEmp.add(notfound);




		JLabel label_19 = new JLabel("Username: ");
		label_19.setBounds(10, 54, 118, 20);
		panel_updtEmp.add(label_19);

		JLabel label_20 = new JLabel("Password: ");
		label_20.setBounds(10, 85, 118, 20);
		panel_updtEmp.add(label_20);

		JLabel label_21 = new JLabel("Firstname: ");
		label_21.setBounds(10, 116, 118, 20);
		panel_updtEmp.add(label_21);

		JLabel label_22 = new JLabel("Lastname: ");
		label_22.setBounds(10, 147, 118, 20);
		panel_updtEmp.add(label_22);

		JLabel label_23 = new JLabel("Date of Birth:");
		label_23.setBounds(10, 178, 118, 20);
		panel_updtEmp.add(label_23);

		JLabel label_24 = new JLabel("Age: ");
		label_24.setBounds(10, 209, 69, 20);
		panel_updtEmp.add(label_24);

		JLabel label_25 = new JLabel("Gender: ");
		label_25.setBounds(10, 240, 118, 20);
		panel_updtEmp.add(label_25);

		JLabel label_26 = new JLabel("Country: ");
		label_26.setBounds(10, 271, 118, 20);
		panel_updtEmp.add(label_26);

		JSeparator separator_2 = new JSeparator();
		separator_2.setOrientation(SwingConstants.VERTICAL);
		separator_2.setBounds(335, 54, 12, 264);
		panel_updtEmp.add(separator_2);

		textField_Username = new JTextField();
		textField_Username.setColumns(10);
		textField_Username.setBounds(138, 48, 146, 26);
		panel_updtEmp.add(textField_Username);

		textField_Password = new JTextField();
		textField_Password.setColumns(10);
		textField_Password.setBounds(138, 79, 146, 26);
		panel_updtEmp.add(textField_Password);

		textField_Firstname = new JTextField();
		textField_Firstname.setColumns(10);
		textField_Firstname.setBounds(138, 110, 146, 26);
		panel_updtEmp.add(textField_Firstname);

		textField_Lastname = new JTextField();
		textField_Lastname.setColumns(10);
		textField_Lastname.setBounds(138, 141, 146, 26);
		panel_updtEmp.add(textField_Lastname);

		JDateChooser dateChooser_1 = new JDateChooser();
		dateChooser_1.setBounds(138, 172, 146, 26);
		panel_updtEmp.add(dateChooser_1);

		textField_Age = new JTextField();
		textField_Age.setColumns(10);
		textField_Age.setBounds(138, 203, 146, 26);
		panel_updtEmp.add(textField_Age);

		JRadioButton radioButton = new JRadioButton("male");
		radioButton.setBounds(134, 231, 56, 29);
		panel_updtEmp.add(radioButton);

		JRadioButton radioButton_1 = new JRadioButton("female");
		radioButton_1.setBounds(192, 231, 92, 29);
		panel_updtEmp.add(radioButton_1);
		bG1.add(radioButton);
		bG1.add(radioButton_1);
		JComboBox comboBox_1 = new JComboBox(cb);
		comboBox_1.setBounds(138, 271, 146, 26);
		panel_updtEmp.add(comboBox_1);

		JLabel label_27 = new JLabel("Address: ");
		label_27.setBounds(380, 54, 103, 20);
		panel_updtEmp.add(label_27);

		JTextArea textArea_1 = new JTextArea();
		textArea_1.setBounds(463, 54, 235, 77);
		panel_updtEmp.add(textArea_1);

		JLabel label_28 = new JLabel("City: ");
		label_28.setBounds(359, 147, 56, 20);
		panel_updtEmp.add(label_28);

		textField_City = new JTextField();
		textField_City.setColumns(10);
		textField_City.setBounds(421, 144, 146, 26);
		panel_updtEmp.add(textField_City);

		JLabel label_29 = new JLabel("Email: ");
		label_29.setBounds(359, 178, 56, 20);
		panel_updtEmp.add(label_29);

		textField_Email = new JTextField();
		textField_Email.setColumns(10);
		textField_Email.setBounds(421, 175, 146, 26);
		panel_updtEmp.add(textField_Email);

		JLabel label_30 = new JLabel("Mobile: ");
		label_30.setBounds(359, 209, 56, 20);
		panel_updtEmp.add(label_30);

		textField_mobil = new JTextField();
		textField_mobil.setColumns(10);
		textField_mobil.setBounds(421, 206, 146, 26);
		panel_updtEmp.add(textField_mobil);

		JLabel label_31 = new JLabel("Employee type:");
		label_31.setBounds(357, 243, 101, 14);
		panel_updtEmp.add(label_31);

		JLabel label_32 = new JLabel("[1] Admin");
		label_32.setBounds(377, 262, 103, 14);
		panel_updtEmp.add(label_32);

		JLabel label_33 = new JLabel("[2] Employee");
		label_33.setBounds(377, 277, 103, 14);
		panel_updtEmp.add(label_33);

		textField_Empt1 = new JTextField();
		textField_Empt1.setBounds(463, 240, 34, 20);
		panel_updtEmp.add(textField_Empt1);
		textField_Empt1.setColumns(10);

		JLabel label_34 = new JLabel("Employee Status:");
		label_34.setBounds(507, 243, 102, 14);
		panel_updtEmp.add(label_34);

		JLabel label_35 = new JLabel("[1] Active");
		label_35.setBounds(507, 262, 89, 14);
		panel_updtEmp.add(label_35);

		JLabel label_36 = new JLabel("[2] Inactive");
		label_36.setBounds(507, 277, 89, 14);
		panel_updtEmp.add(label_36);

		JLabel label_37 = new JLabel("[3] Deactivate");
		label_37.setBounds(507, 293, 89, 14);
		panel_updtEmp.add(label_37);

		textField_Stat1 = new JTextField();
		textField_Stat1.setBounds(609, 240, 34, 20);
		panel_updtEmp.add(textField_Stat1);
		textField_Stat1.setColumns(10);

		JLabel label_38 = new JLabel("");
		label_38.setBounds(260, 13, 164, 14);
		panel_updtEmp.add(label_38);

		JXSearchField srchfldEnterempId = new JXSearchField();
		srchfldEnterempId.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent arg0) {
				try {
					String sql = "Select * from employee_Table where emp_id = '"+srchfldEnterempId.getText().toString()+"' ";
					Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/payroll_final?autoReconnect=true&useSSL=false","root", "root");
					conn.prepareStatement(sql);
					stmt = conn.prepareCall(sql);
					ResultSet rs = stmt.executeQuery(sql);


					rs = stmt.executeQuery(sql);
					System.out.println(sql);
					if (!rs.isBeforeFirst()) {
						System.out.println("no data found");
						notfound.setText("No Record Found");
						java.util.Date d = new java.util.Date();
						d.toString();
						textField_Firstname.setText("");
						textField_Lastname.setText("");
						textField_Username.setText("");
						textField_Password.setText("");
						textField_City.setText("");
						textField_Email.setText("");
						textField_mobil.setText("");
						textField_Age.setText("");
						textField_Empt1.setText("");
						textField_Stat1.setText("");
						textField_jobIdchange.setText("");
						dateChooser_1.setDate(d);
						comboBox_1.setSelectedItem("Select Country");

						textArea_1.setText("");
						radioButton.setSelected(false);
						radioButton_1.setSelected(false);

						// rdbtnAdmin.setSelected(false);
						// rdbtnInactive.setSelected(false);
						//
						// rdbtnActive.setSelected(false);
						// rdbtnInactive.setSelected(false);



					}
					else {

						while(rs.next()){
							notfound.setText("");
							textField_Firstname.setText(rs.getString("first_name"));
							textField_Lastname.setText(rs.getString("last_name"));
							textField_Username.setText(rs.getString("username"));
							textField_Password.setText(rs.getString("passwords"));
							dateChooser_1.setDate(rs.getDate("dob"));
							textField_Age.setText(rs.getString("age"));
							textField_jobIdchange.setText(rs.getString("job_id"));

							String gen1= rs.getString("gender");

							if(gen1.equals("male")){
								radioButton.setSelected(true);
								radioButton_1.setSelected(false);
							}
							else{
								radioButton.setSelected(false);
								radioButton_1.setSelected(true);
							}
		 

							comboBox_1.setSelectedItem(rs.getString("country"));
							textField_City.setText(rs.getString("city"));
							textArea_1.setText(rs.getString("address"));
							textField_Email.setText(rs.getString("email"));
							textField_mobil.setText(rs.getString("mobile_no"));
							textField_Empt1.setText(rs.getString("emp_type"));
							textField_Stat1.setText(rs.getString("status_id"));


						}

					}


				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		srchfldEnterempId.setToolTipText("Search");
		srchfldEnterempId.setPromptFontStyle(2);
		srchfldEnterempId.setPrompt("Enter Employee ID: ");
		srchfldEnterempId.setLayoutStyle(LayoutStyle.VISTA);
		srchfldEnterempId.setBounds(10, 10, 118, 20);
		panel_updtEmp.add(srchfldEnterempId);


		JButton button_1 = new JButton("UPDATE");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String fname1 = textField_Firstname.getText();
				String lname1 = textField_Lastname.getText();
				String Usern1 = textField_Username.getText().toString();
				String Passw1 = textField_Password.getText().toString();
				String City1 = textField_City.getText();
				String emil1 =  textField_Email.getText().toString();
				String num1 = textField_mobil.getText().toString();
				textField_Age.getText().toString();
				java.sql.Date dat1 = new java.sql.Date(dateChooser_1.getDate().getTime());
				String country1 = comboBox_1.getSelectedItem().toString();
				String add1 = textArea_1.getText();
				String gen1  =bG1.getSelection().getActionCommand();
				int changeJob = Integer.parseInt(textField_jobIdchange.getText());
				int emptypers = Integer.parseInt(textField_Empt1.getText());
				int empStatus= Integer.parseInt(textField_Stat1.getText());
				//
				try {
					String sql = "update employee_Table set job_id = '"+changeJob+"',emp_type = '"+emptypers+"', status_id = '"+empStatus+"', username = '"+Usern1+"',passwords = '"+Passw1+"',last_name = '"+lname1+"',first_name ='"+fname1+"',dob = '"+dat1+"',gender = '"+gen1+"',country ='"+country1+ "', city = '"+City1+"', address ='"+add1+"',email='"+ emil1+"', mobile_no = '"+num1+"' where emp_id = '"+srchfldEnterempId.getText().toString()+"' ";

					Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/payroll_final?autoReconnect=true&useSSL=false","root", "root");
					conn.prepareStatement(sql);
					stmt = conn.prepareCall(sql);
					stmt.executeUpdate(sql);
					System.out.println(sql);

					JOptionPane.showMessageDialog(null, "Your Records are Successfully Updated");


				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					//JOptionPane.showMessageDialog(null, e1);

				}
			}
		});

		button_1.setBounds(296, 329, 89, 23);
		panel_updtEmp.add(button_1);

		textField_jobIdchange = new JTextField();
		textField_jobIdchange.setEditable(false);
		textField_jobIdchange.setBounds(650, 146, 34, 22);
		panel_updtEmp.add(textField_jobIdchange);
		textField_jobIdchange.setColumns(10);

		JLabel lblJobId_1 = new JLabel("Job ID");
		lblJobId_1.setBounds(596, 149, 47, 16);
		panel_updtEmp.add(lblJobId_1);
		try {
			stmt = conn.createStatement();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		JPanel panel_addjobdetails = new JPanel();
		tabbedPane_1.addTab("Job Details", null, panel_addjobdetails, null);
		panel_addjobdetails.setLayout(null);


		JLabel lblEmployeeId = new JLabel("Job id");
		lblEmployeeId.setBounds(10, 14, 93, 14);
		panel_addjobdetails.add(lblEmployeeId);

		JLabel lblJobId = new JLabel("Job code");
		lblJobId.setBounds(10, 39, 93, 14);
		panel_addjobdetails.add(lblJobId);

		textField_jobcode = new JTextField();
		textField_jobcode.setBounds(113, 36, 137, 20);
		panel_addjobdetails.add(textField_jobcode);
		textField_jobcode.setColumns(10);

		JLabel lblJobTitle = new JLabel("Job Title");
		lblJobTitle.setBounds(10, 64, 93, 14);
		panel_addjobdetails.add(lblJobTitle);

		textField_jobtitle = new JTextField();
		textField_jobtitle.setBounds(113, 61, 137, 20);
		panel_addjobdetails.add(textField_jobtitle);
		textField_jobtitle.setColumns(10);

		JLabel lblJobSalary = new JLabel("Job Salary");
		lblJobSalary.setBounds(10, 89, 93, 14);
		panel_addjobdetails.add(lblJobSalary);

		textField_salaray = new JTextField();
		textField_salaray.setBounds(113, 86, 137, 20);
		panel_addjobdetails.add(textField_salaray);
		textField_salaray.setColumns(10);

		JButton btnSaveDetails = new JButton("add job details");
		btnSaveDetails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String j_id = textField_id.getText().toString();
				String j_code = textField_jobcode.getText().toString();
				String j_title = textField_jobtitle.getText();
				String j_salary = textField_salaray.getText();

				try {

					String sql = "insert into JobTitle_Table(job_id,job_code,job_title,job_salary) values('"+j_id+"','"+j_code+"','"+j_title+"','"+j_salary+"')";

					stmt.executeUpdate(sql);
					System.out.print(sql);
					JOptionPane.showMessageDialog(null, "Record Added Successfully");


				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, " Duplicate entry ");
					e1.printStackTrace();
				}

			}
		});
		JScrollPane scrollPane_showjobRecords = new JScrollPane();
		scrollPane_showjobRecords.setBounds(278, 11, 420, 311);
		panel_addjobdetails.add(scrollPane_showjobRecords);

		btnSaveDetails.setBounds(54, 114, 127, 23);
		panel_addjobdetails.add(btnSaveDetails);

		table1 = new JTable();
		scrollPane_showjobRecords.setViewportView(table1);
		try {
			stmt = conn.createStatement();
		} catch (SQLException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}

		JButton btnShowAllJob = new JButton("Show all job record");
		btnShowAllJob.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String sqljob = "call viewAllJOb()";

					rs=stmt.executeQuery(sqljob);
					table1.setModel(DbUtils.resultSetToTableModel(rs));


				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnShowAllJob.setBounds(337, 333, 149, 23);
		panel_addjobdetails.add(btnShowAllJob);

		JButton btnNewButton_addJob = new JButton("view job");
		btnNewButton_addJob.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String sqljob = "call addjobz()";

					rs=stmt.executeQuery(sqljob);
					table1.setModel(DbUtils.resultSetToTableModel(rs));


				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_addJob.setBounds(496, 333, 149, 23);
		panel_addjobdetails.add(btnNewButton_addJob);

		textField_id = new JTextField();
		textField_id.setBounds(113, 11, 137, 20);
		panel_addjobdetails.add(textField_id);
		textField_id.setColumns(10);

		JLabel lblNotFound = new JLabel("Record Not Found");
		lblNotFound.setBounds(151, 162, 115, 16);
		panel_addjobdetails.add(lblNotFound);

		JXSearchField searchField_forChangeJob = new JXSearchField();
		searchField_forChangeJob.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String sqlchangeJOb = "Select first_name,last_name,job_id from employee_Table  where emp_id = '"
							+ searchField_forChangeJob.getText().toString() + "' ";
					conn.prepareStatement(sqlchangeJOb);
					stmt = conn.prepareCall(sqlchangeJOb);
					ResultSet rs = stmt.executeQuery(sqlchangeJOb);

					rs = stmt.executeQuery(sqlchangeJOb);
					System.out.println(sqlchangeJOb);
					if (!rs.isBeforeFirst()) {
						lblNotFound.setText("No Record Found");
						System.out.println("no data found");
						textField_lnjobdits.setText("");
						textField_fnjobdits.setText("");
						textField_jobID.setText("");
					} else {
						while (rs.next()) {
							lblNotFound.setText("");
							textField_lnjobdits.setText(rs.getString("first_name"));
							textField_fnjobdits.setText(rs.getString("last_name"));
							textField_jobID.setText(rs.getString("job_id"));
						}
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}


			}
		});


		searchField_forChangeJob.setPrompt("Enter Employee ID");
		searchField_forChangeJob.setBounds(12, 160, 127, 20);
		panel_addjobdetails.add(searchField_forChangeJob);

		textField_lnjobdits = new JTextField();
		textField_lnjobdits.setEditable(false);
		textField_lnjobdits.setBounds(113, 193, 137, 22);
		panel_addjobdetails.add(textField_lnjobdits);
		textField_lnjobdits.setColumns(10);

		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setBounds(10, 196, 91, 16);
		panel_addjobdetails.add(lblLastName);

		JLabel lblFirstName = new JLabel("First Name");
		lblFirstName.setBounds(10, 225, 93, 16);
		panel_addjobdetails.add(lblFirstName);

		textField_fnjobdits = new JTextField();
		textField_fnjobdits.setEditable(false);
		textField_fnjobdits.setBounds(113, 222, 137, 22);
		panel_addjobdetails.add(textField_fnjobdits);
		textField_fnjobdits.setColumns(10);

		textField_jobID = new JTextField();
		textField_jobID.setBounds(113, 257, 137, 22);
		panel_addjobdetails.add(textField_jobID);
		textField_jobID.setColumns(10);

		JLabel lblJobId_2 = new JLabel("Job ID");
		lblJobId_2.setBounds(10, 260, 56, 16);
		panel_addjobdetails.add(lblJobId_2);

		JButton btnUpdateJob = new JButton("Update Job");
		btnUpdateJob.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField_fnjobdits.getText();
				textField_lnjobdits.getText();
				int changeJobx = Integer.parseInt(textField_jobID.getText());

				try {
					String sql = "update employee_Table set job_id = '"+changeJobx+"' where emp_id = '"+searchField_forChangeJob.getText().toString()+"' ";

					Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/payroll_final?autoReconnect=true&useSSL=false","root", "root");
					conn.prepareStatement(sql);
					stmt = conn.prepareCall(sql);
					stmt.executeUpdate(sql);
					System.out.println(sql);

					JOptionPane.showMessageDialog(null, "Successfully Change Job");


				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					//JOptionPane.showMessageDialog(null, e1);

				}

			}
		});
		btnUpdateJob.setBounds(10, 297, 129, 25);
		panel_addjobdetails.add(btnUpdateJob);

		JPanel panel_PendingEmployee = new JPanel();
		panel_PendingEmployee.setToolTipText("");
		tabbedPane_1.addTab("Employee Status", null, panel_PendingEmployee, null);
		panel_PendingEmployee.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 13, 688, 307);
		panel_PendingEmployee.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

		JButton btnShowAllPending = new JButton("Show all Employee Status");
		btnShowAllPending.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String sql = "call viewAllsTATUSUser()";

					rs=stmt.executeQuery(sql);
					table.setModel(DbUtils.resultSetToTableModel(rs));


				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		btnShowAllPending.setBounds(109, 333, 222, 23);
		panel_PendingEmployee.add(btnShowAllPending);

		JButton btnShowAllEmployee_1 = new JButton("Show all Employee Type");
		btnShowAllEmployee_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String sql = "call viewAllTypeUser()";

					rs=stmt.executeQuery(sql);
					table.setModel(DbUtils.resultSetToTableModel(rs));


				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnShowAllEmployee_1.setBounds(343, 333, 216, 25);
		panel_PendingEmployee.add(btnShowAllEmployee_1);



		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Reports", null, panel_1, null);
		panel_1.setLayout(null);

		JTabbedPane tabbedPane_2 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_2.setBounds(12, 13, 709, 397);
		panel_1.add(tabbedPane_2);

		JPanel panel_2 = new JPanel();
		tabbedPane_2.addTab("Generate all Employee", null, panel_2, null);
		panel_2.setLayout(null);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(12, 13, 680, 302);
		panel_2.add(scrollPane_1);

		JTable selectAlltab = new JTable();
		scrollPane_1.setViewportView(selectAlltab);

		JButton btnShowAllEmployee = new JButton("Show All Employee");
		btnShowAllEmployee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String allEmp = "Select * from employee_Table";

					rs=stmt.executeQuery(allEmp);
					selectAlltab.setModel(DbUtils.resultSetToTableModel(rs));


				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		btnShowAllEmployee.setBounds(284, 328, 141, 25);
		panel_2.add(btnShowAllEmployee);

		JLabel label_39 = new JLabel("Logged in As:");
		label_39.setBounds(10, 13, 101, 14);
		contentPane.add(label_39);

		JLabel lblhiz = new JLabel("");
		lblhiz.setBounds(137, 13, 120, 14);
		contentPane.add(lblhiz);
		lblhiz.setForeground(Color.RED);
		lblhiz.setText(String.valueOf(EmployeeName.emp_lstname).toString());

		JButton btnPayroll = new JButton("PAYROLL");
		btnPayroll.setBounds(267, 8, 97, 25);
		contentPane.add(btnPayroll);


		JButton btnLogOut = new JButton("LOG OUT");
		btnLogOut.setBounds(376, 8, 108, 24);
		contentPane.add(btnLogOut);
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new LoginGui();
			}
		});
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				LoginGui log = new LoginGui();	

				dispose(); //to exit

				log.main(null);
				//System.exit(0);
			}
		});
		btnPayroll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				AdminPayrollGui Payframe = new AdminPayrollGui();
				Payframe.setVisible(true);
				
			}
		});



	}
}

