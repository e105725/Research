package app.task;

import javafx.concurrent.Task;

public final class CountUpTask extends Task<Void> {
	private final double progressMax;
	private final double addValue;
	private final long sleepTime;
	
	public CountUpTask(double _progressMax, double _addValue, long _sleepTime) {
		this.progressMax = _progressMax;
		this.addValue = _addValue;
		this.sleepTime = _sleepTime;
	}
	
	@Override
	protected Void call() throws Exception {
		double value = 0;
		while (Double.compare(value, this.progressMax) < 0) {
			if (this.isCancelled()) {
				return null;
			}
			this.updateProgress(value, this.progressMax);
			value += this.addValue;
			Thread.sleep(this.sleepTime);
		}
		return null;
	}
}