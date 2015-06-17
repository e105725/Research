package learn_pinch;

final class Action {
	private final double indexFingerAngleVariation;
	private final double thumbFingerAngleVariation;

	Action(double _indexFingerAngleVariation, double _thumbFingerAngleVariation) {
		this.indexFingerAngleVariation = _indexFingerAngleVariation;
		this.thumbFingerAngleVariation = _thumbFingerAngleVariation;
	}

	final double getIndexFingerAngleVariation() {
		return this.indexFingerAngleVariation;
	}

	final double getThumbFingerAngleVariation() {
		return this.thumbFingerAngleVariation;
	}

	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}

	@Override
	public String toString() {
		return "[" + this.indexFingerAngleVariation + "," + this.thumbFingerAngleVariation + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Action)) {
			return false;
		}
		Action other = (Action) obj;
		
		boolean isIndexFingerAngleEqual = Double.compare(other.indexFingerAngleVariation, this.indexFingerAngleVariation) == 0;
		boolean isThumbAngleEqual = Double.compare(other.thumbFingerAngleVariation, this.thumbFingerAngleVariation) == 0;
		return isIndexFingerAngleEqual && isThumbAngleEqual;
	}
}