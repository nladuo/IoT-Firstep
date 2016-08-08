using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace SerialPortTool
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
            //取消跨线程检查
            Control.CheckForIllegalCrossThreadCalls = false;
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            closeBtn.Enabled = false; //没有打开串口时，无法点击关闭串口
 
            //初始化端口号
            for (int i = 1; i <= 20; i++)
            {
                portComboBox.Items.Add("COM" + i);
            }
            portComboBox.SelectedIndex = 0;

            //初始化波特率
            string[] baudRates = {"300","600","1200","2400",
                                     "4800","9600","19200",
                                     "38400","43000","56000",
                                     "57600","115200" };
            foreach (string baudRate in baudRates)
            {
                baudRateComboBox.Items.Add(baudRate);
            }
            baudRateComboBox.SelectedIndex = 5;

            /************新增代码*********/
            rTrackBar.Enabled = false;
            gTrackBar.Enabled = false;
            bTrackBar.Enabled = false;

            rLabel.Text = "R:0";
            gLabel.Text = "G:0";
            bLabel.Text = "B:0";
            /****************************/

        }

        //打开串口
        private void openBtn_Click(object sender, EventArgs e)
        {
            try
            {
                serialPort1.PortName = portComboBox.Text;
                serialPort1.BaudRate = int.Parse(baudRateComboBox.Text);
                serialPort1.Open();
                openBtn.Enabled = false;
                closeBtn.Enabled = true;
                rTrackBar.Enabled = true;
                gTrackBar.Enabled = true;
                bTrackBar.Enabled = true;
                //打开串口之后波特率和端口号就不能改了
                baudRateComboBox.Enabled = false;
                portComboBox.Enabled = false;
                
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        //关闭串口
        private void closeBtn_Click(object sender, EventArgs e)
        {
            try
            {
                serialPort1.Close();
                openBtn.Enabled = true;
                closeBtn.Enabled = false;

                baudRateComboBox.Enabled = true;
                portComboBox.Enabled = true;
                rTrackBar.Enabled = false;
                gTrackBar.Enabled = false;
                bTrackBar.Enabled = false;
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }


        private void onTrackBarChanged(object sender, EventArgs e)
        {
            byte[] data = new byte[3] { (byte)rTrackBar.Value, (byte)(gTrackBar.Value+50), (byte)(bTrackBar.Value+100) };
            serialPort1.Write(data, 0, 3);
            rLabel.Text = "R:" + rTrackBar.Value;
            gLabel.Text = "G:" + gTrackBar.Value;
            bLabel.Text = "B:" + bTrackBar.Value; 
        }
    }
}
