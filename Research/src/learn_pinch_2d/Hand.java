package learn_pinch_2d;


//手のモデル
//ただ持ってるのは人差し指と親指のjointだけ
final class Hand {
	final ThumbFinger thumbFinger;
	final IndexFinger indexFinger;
	
	Hand() {
		this.thumbFinger = new ThumbFinger();
		this.indexFinger = new IndexFinger();
		this.reset();
	}

	final void reset() {
		this.thumbFinger.reset();
		this.indexFinger.reset();
	}
}