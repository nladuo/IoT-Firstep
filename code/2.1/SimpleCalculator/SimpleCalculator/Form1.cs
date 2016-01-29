using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace SimpleCalculator
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            try
            {
                int inputNum1 = int.Parse(input1.Text);
                int inputNum2 = int.Parse(input2.Text);
                int resultNum = 0;
                switch (comboBox1.Text)
                {
                    case "+":
                        resultNum = inputNum1 + inputNum2;
                        break;
                    case "-":
                        resultNum = inputNum1 - inputNum2;
                        break;
                    case "*":
                        resultNum = inputNum1 * inputNum2;
                        break;
                    case "/":
                        resultNum = inputNum1 / inputNum2;
                        break;
                }
                MessageBox.Show(input1.Text + comboBox1.Text + input2.Text + "=" + resultNum);
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
            
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            input1.Text = "0";
            input2.Text = "0";
            comboBox1.SelectedText = "+";
            comboBox1.Items.Add("+");
            comboBox1.Items.Add("-");
            comboBox1.Items.Add("*");
            comboBox1.Items.Add("/");
        }

    }
}
