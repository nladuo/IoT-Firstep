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
            sendBtn.Enabled = false;
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
                //打开串口之后波特率和端口号就不能改了
                baudRateComboBox.Enabled = false;
                portComboBox.Enabled = false;
                sendBtn.Enabled = true;
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
                sendBtn.Enabled = false;
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        //发送数据
        private void sendBtn_Click(object sender, EventArgs e)
        {
            try
            {
                int val = int.Parse(sendTBox.Text);
                if (val >= 150 || val < 0)
                {
                    MessageBox.Show("请输入0-149之间的数字");
                    return;
                }
                byte[] data = new byte[1]{(byte)val};
                serialPort1.Write(data, 0, 1);
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }
    }
}
