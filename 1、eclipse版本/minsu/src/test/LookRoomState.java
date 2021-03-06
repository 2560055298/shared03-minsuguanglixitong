package test;

import java.awt.BorderLayout;
import java.awt.Container;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import pojo.Customer;
import serviceImpl.CustomerServiceImpl;
import util.JdbcUtil;

/**
 * 查看房间入住状态
 * @author 羊羊羊
 *
 */
public class LookRoomState extends JFrame{
	CustomerServiceImpl cSI;		//业务代码层（顾客表）对象
	
	public LookRoomState() {
		//1、设置窗体大小
		setBounds(100, 100, 500, 495);
		
		//2、设置窗口:居中
		setLocationRelativeTo(null);
		
		//3、设置关闭窗体的：只关闭窗体，不关闭程序
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		
		//4、设置标题
		setTitle("房间状态");
		
		//5、从窗口中：获取容器
		Container c = getContentPane();
		
		//6、设置（边界布局）border布局
		c.setLayout(new BorderLayout());
		
		//6、生成：上面板、中面板、下面板
		JPanel pUp = new JPanel();
		JPanel pCenter = new JPanel();
		JPanel pDown = new JPanel();
		
		//7、获取房间表的（结果集）:
		ResultSet rs = JdbcUtil.getResultSet("select * from room");
	
		
		//8、生成：表的模型
		DefaultTableModel dt = new DefaultTableModel();
		JTable table = new JTable(dt);
		
		//9、获取字符集数据
		ResultSetMetaData rsmd = null;
		try {
			rsmd = rs.getMetaData();
			int numRSColumn = rsmd.getColumnCount();	//获取字符集数据的：列数
			
			dt.addColumn("顾客姓名");
			//将列名，加入表模型
//			for(int i = 1; i <= numRSColumn; i++) {
//				dt.addColumn(rsmd.getColumnName(i));
//			}
			
			dt.addColumn("房间号");
			dt.addColumn("房间类型");
			dt.addColumn("房间价格");
			dt.addColumn("房间状态");
			
			Vector<Object> newRow;
			cSI = new CustomerServiceImpl();
			while(rs.next()) {
				newRow = new Vector<>();
				Customer customer = cSI.selCustomerInfoByCustomer(rs.getInt("rNum"));
				if(customer != null) {
					newRow.addElement(customer.getcName());
				}else {
					newRow.addElement("无");
				}
				newRow.addElement(rs.getInt("rNum"));
				newRow.addElement(rs.getString("rType"));
				newRow.addElement(rs.getInt("rPrice"));
				newRow.addElement(rs.getString("rState"));
				dt.addRow(newRow);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//10、生成标签
		JLabel lUp = new JLabel("房间入住表");
		
		//11、添加图片
		JLabel l = new JLabel();
		Icon icon = new ImageIcon("image\\房间信息背景.jpg");
		l.setIcon(icon);
		l.setSize(1000,580);
		l.setLocation(0, 0);
		
		//12、在上面板中：添加标题
		pUp.add(lUp);	
		
		//13、将table表添加到：可滚动板上，并将其添加到：中面板中
		JScrollPane scrollPane = new JScrollPane(table);
		pCenter.add(scrollPane);
		
		//14、在下面板中：添加标题
		pDown.add(l);
		
		//15、将上面板，和中面板添加：容器中
		c.add(pUp, BorderLayout.NORTH);
		c.add(pCenter, BorderLayout.CENTER);
		c.add(pDown, BorderLayout.SOUTH);
		
		//16、设置窗口大小：不可变
		setResizable(false);
		
		//17、设置table表：表头和列都（不能拖动）
		table.getTableHeader().setReorderingAllowed(false);		//设置属性列位置：不能移动
		table.getTableHeader().setResizingAllowed(false);		//设置间距：不可更改
		table.enable(false);		//设置内容：不可以修改
	}
	
	public static void main(String[] args) {
		LookRoomState l = new LookRoomState();
		l.setVisible(true); 
		
		
	}
}
