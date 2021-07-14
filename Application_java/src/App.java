import java.awt.EventQueue;
import net.proteanit.sql.DbUtils;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ScrollPaneConstants;

public class App {

	private JFrame frame;
	private JTextField textbtn;
	private JTextField textEdi;
	private JTextField BookSearch;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App window = new App();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public App() {
		initialize();
		Connect();
		table_load();
	}
	
	Connection conn;
	PreparedStatement ptSt;
	ResultSet rst;
	private JTextField textStuid;
	private JTable table;
	
	public void Connect()
	{
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/appbook","root","");
			
		} 
		catch (ClassNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (SQLException e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	public void table_load()
	{
		try 
		{
			ptSt = conn.prepareStatement("select * from books_info");
			rst = ptSt.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rst));
			
		}
		catch(SQLException e1)
		{
			e1.printStackTrace();
		}
	}
		

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 15));
		frame.setBounds(100, 100, 942, 507);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("              Book Rental System");
		lblNewLabel.setBounds(86, 11, 769, 68);
		lblNewLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 40));
		frame.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Registation", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(10, 90, 358, 291);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Book Name");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_1.setBounds(25, 127, 125, 41);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Edition");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblNewLabel_1_1.setBounds(25, 201, 125, 49);
		panel.add(lblNewLabel_1_1);
		
		textbtn = new JTextField();
		textbtn.setBounds(160, 119, 168, 49);
		panel.add(textbtn);
		textbtn.setColumns(10);
		
		textEdi = new JTextField();
		textEdi.setColumns(10);
		textEdi.setBounds(160, 201, 168, 47);
		panel.add(textEdi);
		
		JLabel lblNewLabel_1_3 = new JLabel("Student Id");
		lblNewLabel_1_3.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_1_3.setBounds(25, 37, 125, 57);
		panel.add(lblNewLabel_1_3);
		
		textStuid = new JTextField();
		textStuid.setForeground(Color.LIGHT_GRAY);
		textStuid.setColumns(10);
		textStuid.setBounds(160, 49, 168, 41);
		panel.add(textStuid);
		
		JButton btnNewButton = new JButton("Save");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String  bname, edition  , studentId;
				
				bname =  textbtn.getText();
				edition = textEdi.getText();
				studentId =  textStuid.getText();
				
				try {               
					
					ptSt = conn.prepareStatement("insert into books_info(bookname,bookedition,StudentId)values(?,?,?)");
					ptSt.setString(1, bname);
					ptSt.setString(2, edition);
					ptSt.setString(3, studentId);
					ptSt.executeUpdate();
					JOptionPane.showMessageDialog(null,"Record Added!!!!!");
					table_load();
					textbtn.setText("");
					textEdi.setText("");
					textStuid.setText("");
					
					textbtn.requestFocus();
					
				}
				catch(SQLException e1) 
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
				
				
			}
		});
		btnNewButton.setBounds(66, 406, 102, 50);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				textbtn.setText("");
				textEdi.setText("");
				textStuid.setText("");
				
			}
		});
		btnClear.setBounds(210, 406, 102, 50);
		frame.getContentPane().add(btnClear);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(null, "Search", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_3.setBounds(378, 90, 263, 291);
		frame.getContentPane().add(panel_3);
		panel_3.setLayout(null);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Book Id");
		lblNewLabel_1_1_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNewLabel_1_1_1.setBounds(21, 44, 216, 61);
		panel_3.add(lblNewLabel_1_1_1);
		
		BookSearch = new JTextField();
		BookSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				try {
					String id = BookSearch.getText();   
					ptSt = conn.prepareStatement("select bookname,bookedition,StudentId from books_info where bookId = ?");
					ptSt.setString(1, id);
					ResultSet rs = ptSt.executeQuery();
					
					if(rs.next() == true )
					{
						
						String name =  rs.getString(1);
						String edition = rs.getString(2);
						String studentId =  rs.getString(3);
						
						textbtn.setText(name);
						textEdi.setText(edition);
						textStuid.setText(studentId);
						
					}
					else
					{
						textbtn.setText("");
						textEdi.setText("");
						textStuid.setText("");
						
					}
					
				}
				catch(SQLException e1) 
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
					
				}
				
			}
		});
		BookSearch.setColumns(10);
		BookSearch.setBounds(21, 116, 216, 82);
		panel_3.add(BookSearch);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String  bname, edition  , studentId , bId;
				
				bname =  textbtn.getText();
				edition = textEdi.getText();
				studentId =  textStuid.getText();
				bId =  BookSearch.getText();
				
				try {
					
					if(textbtn.getText().isEmpty() && textEdi.getText().isEmpty() && textStuid.getText().isEmpty() && BookSearch.getText().isEmpty() )
					{
						
						JOptionPane.showMessageDialog(null,"Enter valid values ");
						textbtn.setText("");
						textEdi.setText("");
						textStuid.setText("");
					
						 
					}
					else
					{
						ptSt = conn.prepareStatement("update books_info set bookname = ? , bookedition = ? , StudentId = ? where bookId = ?");
						ptSt.setString(1, bname);
						ptSt.setString(2, edition);
						ptSt.setString(3, studentId);
						ptSt.setString(4, bId);
						ptSt.executeUpdate();
						JOptionPane.showMessageDialog(null,"Record Updated!!!!!");
						table_load();
						textbtn.setText("");
						textEdi.setText("");
						textStuid.setText("");
						textbtn.requestFocus();
						
					}
									
				}
				catch(SQLException e1) 
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				
				
			}
		});
		btnUpdate.setBounds(523, 402, 102, 59);
		frame.getContentPane().add(btnUpdate);
		
		JButton btnUpdate_1 = new JButton("Delete");
		btnUpdate_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String bId , stuId;
				bId =  BookSearch.getText();
				
				try {
					if(BookSearch.getText().isEmpty())
					{
						JOptionPane.showMessageDialog(null,"Enter valid values ");
						textbtn.setText("");
						textEdi.setText("");
						textStuid.setText("");
						
						
					}
					else
					{
						ptSt = conn.prepareStatement("delete from books_info where bookId = ?");
						ptSt.setString(1, bId);
						ptSt.executeUpdate();
						JOptionPane.showMessageDialog(null,"Record Deleted!!!!!");
						table_load();
						textbtn.setText("");
						textEdi.setText("");
						textStuid.setText("");
					
						textbtn.requestFocus();
					}
					
					
				}
				catch(SQLException e1) 
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
			}
		});
		btnUpdate_1.setBounds(411, 402, 102, 59);
		frame.getContentPane().add(btnUpdate_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(651, 90, 265, 366);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
	}
}
