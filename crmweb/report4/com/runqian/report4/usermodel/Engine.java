
package com.runqian.report4.usermodel;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import com.runqian.base4.resources.EngineMessage;
import com.runqian.base4.resources.MessageManager;
import com.runqian.base4.util.Logger;
import com.runqian.base4.util.Sentence;
import com.runqian.base4.util.StringUtils;
import com.runqian.report4.control.ControlUtils;
import com.runqian.report4.model.CalcReport;
import com.runqian.report4.model.engine.ExtCellSet;
import com.runqian.report4.model.engine2.RowReport;
/**
 * @since 20180207
 * @see 润乾破解文件，替换该文件即可不需要license
 * @author liuyx
 *
 */
public class Engine {
	private static MessageManager localMessageManager = EngineMessage.get();

	private IReport _$1 = null;

	private Context _$2 = null;

	private static String _$3 = localMessageManager.getMessage("Engine.start");

	private static String _$4 = localMessageManager.getMessage("Engine.startCalc");

	private static String _$5 = localMessageManager.getMessage("Engine.end");

	private IReport _$6;

	public Engine(IReport paramIReport, Context paramContext) {
		this._$1 = paramIReport;
		this._$2 = paramContext;
		CalcReport.prepareCalculate(paramIReport, paramContext);
	}

	private String _$1(String paramString, int paramInt) {
		StringBuffer localStringBuffer = new StringBuffer();
		for (int i = 0; i < paramString.length(); i++) {
			localStringBuffer.append((char) (paramString.charAt(i) + paramInt));
		}
		return localStringBuffer.toString();
	}

	private byte[] _$1(String paramString, boolean paramBoolean) {
		if ((paramString == null) || (paramString.trim().length() == 0))
			return null;
		Object localObject1 = null;
		try {
			if (new File(paramString).exists()) {
				localObject1 = new FileInputStream(paramString);
			} else if (this._$2.getApplication() != null) {
				if (!(paramString = StringUtils.replace(paramString, "\\", "/")).startsWith("/")) {
					paramString = "/" + paramString;
				}
				localObject1 = this._$2.getApplication().getResourceAsStream(paramString);
			}
			if (localObject1 == null) {
				String str = Context.getMainDir();
				if (!paramString.startsWith("/"))
					paramString = "/" + paramString;
				paramString = str + paramString;
				if (new File(paramString).exists()) {
					localObject1 = new FileInputStream(paramString);
				} else if (this._$2.getApplication() != null) {
					if (!(paramString = StringUtils.replace(paramString, "\\", "/")).startsWith("/")) {
						paramString = "/" + paramString;
					}
					localObject1 = this._$2.getApplication().getResourceAsStream(paramString);
				}
			}
			if ((localObject1 == null) && (paramBoolean)) {
				localObject1 = getClass().getResourceAsStream("/com/runqian/report4/ide/resources/app_logo.jpg");
			}
			if (localObject1 == null) {
				return null;
			}
			return ControlUtils.getStreamBytes((InputStream) localObject1);
		} catch (Exception localException1) {
			byte[] arrayOfByte;
			return null;
		} finally {
			try {
				if (localObject1 != null)
					((InputStream) localObject1).close();
			} catch (Exception localException2) {
			}
		}
	}

	public IReport calc() {
		PerfMonitor.checkOutOfMemory();
		Object localObject1 = null;
		INormalCell localINormalCell = null;
		int i = 0;
		ExtCellSet localExtCellSet;
		if (((localExtCellSet = ExtCellSet.get()) == null) || (!localExtCellSet.checkExpiration())) {
			MessageManager localMessageManager = EngineMessage.get();
			//序列号过期验证失败，抛出异常，这里屏蔽异常抛出，让程序继续执行
			//throw new RuntimeException(localMessageManager.getMessage("Engine.outofValidTime"));
			System.err.println(localMessageManager.getMessage("Engine.outofValidTime"));
		}
		boolean bool1 = (localExtCellSet.getType() == 0) || (localExtCellSet.getVersion() == 20);
		boolean bool2;
		int k;
		int m;
		if ((bool2 = "$`0$".equals(this._$1.getTip()))) {
			if ((localINormalCell = this._$1.getCell(1, (short) 1)) != null) {
				localINormalCell.setValue(" ");
			}
			String str = _$1("&}TaSa", -2);
			int j = this._$1.getRowCount();
			k = this._$1.getColCount();
			for (m = 2; m <= j; m++) {
				for (int n = 1; n <= k; n = (short) (n + 1)) {
					if ((localINormalCell = this._$1.getCell(m, (short)n)) != null) {
						IByteMap localIByteMap;
						if ((localIByteMap = localINormalCell.getExpMap()) != null) {
							Object localObject5;
							if ((localObject5 = localIByteMap.get((byte) 40)) != null) {
								localObject5 = Sentence.replace((String) localObject5, "select", str, 1);
								localIByteMap.put((byte) 40, localObject5);
							}
						}
					}
				}
			}
		}
		PerfMonitor.enterTask(bool1);
		try {
			Logger.info(_$3);
			CalcReport.calcDataSet(this._$1, this._$2, true);
			PerfMonitor.increaseCellNum(k = PerfMonitor.getFutureCellNum(this._$1, this._$2));
			i = k;
			Object localObject4;
			if ((m = this._$1.getReportType()) == 1) {
				localObject4 = new RowReport(this._$1);
				this._$6 = ((IReport) localObject4);
				Logger.info(_$4);
				((RowReport) localObject4).calculate(this._$2);
				Logger.info(_$5);
				((RowReport) localObject4).reflush();
				localObject1 = localObject4;
			} else {
				localObject4 = new ExtCellSet(this._$1);
				this._$6 = ((IReport) localObject4);
				Logger.info(_$4);
				((ExtCellSet) localObject4).calculate(this._$2);
				Logger.info(_$5);
				((ExtCellSet) localObject4).reflush();
				localObject1 = localObject4;
			}
			processImageFile((IReport)localObject1);
		} catch (OutOfMemoryError localOutOfMemoryError) {
			this._$1 = null;
			localObject1 = null;
			Logger.error("Out of Memory", localOutOfMemoryError);
			PerfMonitor.fireOutOfMemory();
		} finally {
			PerfMonitor.leaveTask();
			PerfMonitor.reduceCellNum(i);
			this._$6 = null;
		}
		return (IReport)localObject1;
	}

	public void interrupt() {
		if (this._$6 != null)
			if ((this._$6 instanceof ExtCellSet))
				((ExtCellSet) this._$6).interrupt();
			else if ((this._$6 instanceof RowReport))
				((RowReport) this._$6).interrupt();
	}

	public void processImageFile(IReport paramIReport) {
		BackGraphConfig localBackGraphConfig;
		if (((localBackGraphConfig = paramIReport.getBackGraphConfig()) != null) && (localBackGraphConfig.getImageBytes() == null)) {
			if (localBackGraphConfig.getType() == 1) {
				localBackGraphConfig.generateImage(this._$2);
			} else {
				byte[] arrayOfByte1;
				if ((arrayOfByte1 = _$1(localBackGraphConfig.getURLOrClassName(), false)) != null) {
					localBackGraphConfig.setImageBytes(arrayOfByte1);
				}
			}
		}
		int i = paramIReport.getRowCount();
		short s1 = paramIReport.getColCount();
		for (int j = 1; j <= i; j++)
			for (short s2 = 1; s2 <= s1; s2 = (short) (s2 + 1)) {
				INormalCell localINormalCell;
				if ((localINormalCell = paramIReport.getCell(j, s2)) != null) {
					Area localArea;
					if ((!localINormalCell.isMerged()) || (((localArea = localINormalCell.getMergedArea()).getBeginRow() == j) && (localArea.getBeginCol() == s2))) {
						int k;
						Object localObject1;
						Object localObject2;
						if ((k = localINormalCell.getCellType()) == -63) {
							if (((localObject1 = localINormalCell.getValue()) != null) && ((localObject1 instanceof String)) && (localObject1.toString().trim().length() > 0)) {
								localObject2 = localObject1.toString().trim();
								byte[] arrayOfByte2;
								if ((arrayOfByte2 = _$1((String) localObject2, true)) == null) {
									continue;
								}
								localINormalCell.setValue(arrayOfByte2);
							}
						}
						if (((localObject1 = localINormalCell.getCellGraphConfig()) != null) && (((CellGraphConfig) localObject1).getImageBytes() == null))
							if (((CellGraphConfig) localObject1).getType() == 1) {
								((CellGraphConfig) localObject1).generateImage(this._$2);
							} else if ((localObject2 = _$1(((CellGraphConfig) localObject1).getURLOrClassName(), false)) != null) {
								((CellGraphConfig) localObject1).setImageBytes((byte[]) localObject2);
							}
					}
				}
			}
	}
}