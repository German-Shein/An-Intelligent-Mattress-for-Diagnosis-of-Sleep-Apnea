using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Intelligent_Mattress_Data_Visualizer
{
	public interface Subject
	{
		void Register_Observer (Observer observer);
		void Remove_Obsever (Observer observer);
		void Notify_Observers ();
	}
}
