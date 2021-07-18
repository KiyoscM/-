# 1. はじめに

本リポジトリは，学部3年次に行ったアプリの共同開発の授業で制作したゲームのソースコードを置いています．
開発時のリポジトリはプライベートのため，こちらにまるごとコピーしました．

残念ながら，現在はデータベースを削除してしまっているため遊ぶことができません．
しかし成果として，どのようなアプリであったかを残したいと考え，こちらを用意しました．

開発メンバーは5人で，
[Hiroyuki Okumura](https://github.com/h1royuki229){:target="_blank"}，
[high-tail](https://github.com/high-tail){:target="_blank"}，
[Takamichi Wada](https://github.com/wadayamada){:target="_blank"}と，
マネージャが1人です．

私は，次項で述べますシングルプレイモードのアルゴリズムと実装に携わりました．

# 2. アプリの概要

![homeSample](images/homeSample.png){:target="_blank"}

本アプリは，[日本漢字能力検定](https://www.kanken.or.jp/kanken/){:target="_blank"}の受検者をターゲットとした，**漢字学習支援のためのパズルゲームアプリ**です．
漢字学習をパズルゲームにしてしまうことで，利用者が楽しく学習を進めることができるのではないかというコンセプトがあります．

利用者ごとに難易度を設定することができ，選択できる難易度は，

* 5級・・・小学校6年生修了程度
* 4級・・・中学校在学程度
* 3級・・・中学校卒業程度
* 準2級・・・高校在学程度
* 2級・・・高校卒業・大学・一般程度

の5段階です．

漢検には「読み」と「書き」がありますが，私たちは「熟語」に注目し，**熟語を正しくを読むことができれば，漢字のパネルを消すことができる**という仕組みでパズルに組み込んでいます．そのためとりわけ「読み」の学習に特化したアプリです．

どのようなパズルゲームなのかは，モードによって異なります．モードは，

* シングルプレイモード・・・1人用
* バトルロワイヤルモード・・・2～4人用，通信に対応

の2つから選択できます．

***

## シングルプレイモード

![how_to_play_single_part1](images/how_to_play_single_part1.png)

![how_to_play_single_part2](images/how_to_play_single_part2.png)

![how_to_play_single_part3](images/how_to_play_single_part3.png)

![how_to_play_single_part4](images/how_to_play_single_part4.png)

![how_to_play_multi_part1](images/how_to_play_multi_part1.png)

![how_to_play_multi_part2](images/how_to_play_multi_part2.png)

![how_to_play_multi_part3](images/how_to_play_multi_part3.png)

![how_to_play_multi_part4](images/how_to_play_multi_part4.png)

