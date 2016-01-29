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
            //设置DataReceived的委托
            serialPort1.DataReceived += serialPort1_DataReceived;
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

        private void serialPort1_DataReceived(object sender, System.IO.Ports.SerialDataReceivedEventArgs e)
        {
            try
            {
                string receivedText = serialPort1.ReadExisting();
                receivedTBox.Text += receivedText;
                //滚动条滚到底端
                receivedTBox.SelectionStart = receivedTBox.Text.Length;
                receivedTBox.ScrollToCaret();
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
            
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
                serialPort1.WriteLine(sendTBox.Text);
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }
    }
}
