package com.tomoyashibata.viewpager2_sample

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.tomoyashibata.viewpager2_sample.databinding.ItemVideoProgramBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
  private val tag: String by lazy { this.localClassName }

  // ViewPager2 で表示したいデータの List
  private val videoPrograms = arrayListOf(
    VideoProgram(
      title = "美しき世界の山々「サザンアルプス（ニュージーランド）」",
      description = "ニュージーランドの南島を貫くサザンアルプス、原生林と白き峰々が美しい山脈だ。海岸部は氷河の侵食作用でできたフィヨルドが切れ込み、山ろくには温帯雨林と呼ばれる湿潤な森が広がる。キツネなどが住んでいなかったため、天敵を知らない野鳥が近くまで寄ってくる。森林限界を超えると、サザンアルプスの高峰が姿を現す。南半球のマッターホルンと呼ばれるアスパイアリング（３０３３ｍ）をめざして岩壁をよじ登り頂をめざす。"
    ),
    VideoProgram(
      title = "ＮＨＫニュース　おはよう日本▼観測史上最強レベルの寒気が北海道に",
      description = "▼観測史上最強レベルの寒気が北海道上空に　土曜は首都圏でも積雪のおそれ　▼女児虐待　児童相談所のずさんな対応　▼勝てるキャッチャーを目指して“甲斐キャノン”に中日の元監督・谷繁元信さんが迫る　▼「昭和の戦争」と「平成のテロ」で肉親を奪われた男性　次の時代に伝えたいメッセージ　▼東京タワー　見守る人の心も照らすライトアップ３０年の歴史"
    ),
    VideoProgram(
      title = "ＮＨＫニュース　おはよう日本",
      description = "蒸した麺を乾かしてからお湯をかけるも、麺が思うように柔らかくなりません。萬平さんと福ちゃんがラーメンの常温保存の方法でつまづいている頃、タカちゃんの妹・吉乃ちゃんを巡り、男同士の戦いが勃発。神部さんの同僚の岡さんと森本さんが、それぞれ一目惚れしたことを明かし、吉乃ちゃんを射止めるべく、正々堂々勝負することを誓います。　福子：安藤サクラ／萬平：長谷川博己／鈴：松坂慶子／神部：瀬戸康史"
    ),
    VideoProgram(
      title = "あさイチ「プレミアムトーク　生田斗真」",
      description = "プレミアムトーク　生田斗真【キャスター】博多華丸・大吉、近江友里恵"
    ),
    VideoProgram(
      title = "チコちゃんに叱られる！「歩いていると靴に小石が入るのはなぜ？ほか」",
      description = "小石の問題では、ユニークな実験によって意外な事実が明らかになります。ホットドックの問いでは歴史ドラマに初登場のある方が登場します。もみあげの疑問でも日本の歴史の一端が垣間見えます。身近なのに考えたことのない疑問ばかり。学校や家族の間で「知ってた？」と話題になること間違いなし。物事の裏を探っていく楽しさを味わってください。キョエちゃんのコーナーは、リアル５才からの鋭い質問にお答えします。"
    )
  )

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    this.setupMainViewPager2()

    // 水平方向・垂直方向に切り替える処理をボタンにセットしている
    this.switch_horizaontal_button.setOnClickListener {
      this.setMainViewPagerOrientation(ViewPager2.ORIENTATION_HORIZONTAL)
      Toast.makeText(this, "SWITCH TO HORIZONTAL", Toast.LENGTH_SHORT).show()
    }
    this.switch_vertical_button.setOnClickListener {
      this.setMainViewPagerOrientation(ViewPager2.ORIENTATION_VERTICAL)
      Toast.makeText(this, "SWITCH TO VERTICAL", Toast.LENGTH_SHORT).show()
    }
  }

  // ViewPager2 のセットアップ
  private fun setupMainViewPager2() {
    this.main_view_pager2.adapter = MainViewPagerAdapter(this.videoPrograms)
    this.setMainViewPagerOrientation(ViewPager2.ORIENTATION_HORIZONTAL)

    this.main_view_pager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
      override fun onPageScrollStateChanged(state: Int) {
        // スクロール状態が切り替わると呼ばれる
        //
        // SCROLL_STATE_IDLE     = 0; => スクロールを待機
        // SCROLL_STATE_DRAGGING = 1; => スクロールが開始した
        // SCROLL_STATE_SETTLING = 2; => 表示する画面が決定された
        super.onPageScrollStateChanged(state)
        Log.d("$tag: onPageScrollStateChanged", state.toString())
      }

      // スクロールイベントが発生している最中リアルタイムに呼ばれる
      //
      // position             => 現在の position が渡される
      //                         スクロールが完了して直前と違う position になっているとその値が反映される
      // positionOffset       => スクロール開始前からの相対的な offset 値
      // positionOffsetPixels => スクロール開始前からの相対的な offset 値（ピクセル）
      //
      override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        super.onPageScrolled(position, positionOffset, positionOffsetPixels)
        Log.d(
          "$tag: onPageScrolled",
          "position => $position, positionOffset => $positionOffset, positionOffsetPixels => $positionOffsetPixels"
        )
      }

      // スクロールが終了すると選択されたページの position を引数にして呼ばれる
      override fun onPageSelected(position: Int) {
        super.onPageSelected(position)
        Log.d("$tag: onPageSelected", position.toString())
      }
    })
  }

  // ViewPager2 を水平方向・垂直方向に切り替える
  private fun setMainViewPagerOrientation(@ViewPager2.Orientation orientation: Int) {
    this.main_view_pager2.orientation = orientation
  }

  // ViewPager2 に渡す RecyclerView.Adapter
  class MainViewPagerAdapter(
    private val videoPrograms: List<VideoProgram>
  ) : RecyclerView.Adapter<MainViewPagerAdapter.BindingViewHolder>() {
    override fun onCreateViewHolder(
      parent: ViewGroup,
      viewType: Int
    ): MainViewPagerAdapter.BindingViewHolder {
      val inflater = LayoutInflater.from(parent.context)
      val binding = DataBindingUtil.inflate<ItemVideoProgramBinding>(
        inflater,
        R.layout.item_video_program,
        parent,
        false
      )
      return BindingViewHolder(binding)
    }

    override fun getItemCount(): Int = this.videoPrograms.count()

    override fun onBindViewHolder(holder: MainViewPagerAdapter.BindingViewHolder, position: Int) {
      holder.binding.videoProgram = this.videoPrograms[position]
      holder.binding.executePendingBindings()
    }

    class BindingViewHolder(val binding: ItemVideoProgramBinding) :
      RecyclerView.ViewHolder(binding.root)
  }
}
