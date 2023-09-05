package com.example.bni_test.screen.caseAppPromo.halamanUtamaPromo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.bni_test.modal.ModelPromo
import org.json.JSONArray

class PromoViewModel(application: Application) : AndroidViewModel(application) {

    private var _promos = MutableLiveData<List<ModelPromo>>()
    val promos: LiveData<List<ModelPromo>>
        get() = _promos

    private val _volleyRun = MutableLiveData<Boolean>()
    val volleyRun: LiveData<Boolean>
        get() = _volleyRun


    fun runVolleySources() {
        if (_volleyRun.value == true) return


        _volleyRun.value = true

        val stringRequest = object : StringRequest(
            Method.GET,
            "https://content.digi46.id/promos",
            { response: String ->

                _volleyRun.value = false

                val jsonArray = JSONArray(response)

                val listPromo = ArrayList<ModelPromo>()
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    var jsonObjectImg = jsonObject.getJSONObject("img")
                    jsonObjectImg = jsonObjectImg.getJSONObject("formats")
                    val jsonImageSmall = jsonObjectImg.getJSONObject("small")
                    val jsonImageMedium = jsonObjectImg.getJSONObject("medium")
                    listPromo.add(
                        ModelPromo(
                            jsonObject.getString("nama"),
                            jsonObject.getString("desc"),
                            jsonImageSmall.getString("url"),
                            jsonImageMedium.getString("url"),
                            jsonObject.getString("createdAt")
                        )
                    )

                    _promos.value = listPromo

                }


                //
            },
            Response.ErrorListener {
                _volleyRun.value = false

            }
        ) {

        }
        stringRequest.retryPolicy = DefaultRetryPolicy(
            30000,//40 detik
            1,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        Volley.newRequestQueue(getApplication()).add(stringRequest)
    }

    fun testingBypas(){
        val jsonArray = JSONArray("""
            [
              {
                "id": 8,
                "Title": null,
                "published_at": "2021-04-14T03:35:09.233Z",
                "created_at": "2021-04-14T03:35:07.506Z",
                "updated_at": "2021-07-31T14:48:46.361Z",
                "name_promo": null,
                "desc_promo": null,
                "nama": "BNI Credit Card",
                "desc": "Potongan langsung (diskon) Rp 150.000,- untuk minimal transaksi Rp 1.000.000, kuota 15 transaksi pertama per hari.\n- Berlaku tiap Kamis dan Jumat.\n- Berlaku untuk pembelian Tiket Sriwijaya Air dan NAM Air di Website dan Mobile Apps Sriwijaya Air\n- Potongan harga langsung diperoleh ketika nomor Kartu BNI dimasukkan (tanpa kode promo)\n- Syarat dan ketentuan berlaku\nInfo lebih lanjut hubungi BNI Call 1500046",
                "latitude": "-6.203606",
                "longitude": "106.803022",
                "alt": "17",
                "createdAt": "2021-04-13",
                "lokasi": "Pejompongan",
                "count": 14,
                "img": {
                  "id": 3,
                  "name": "bni-credit-card-banner-fitur-mbanking-small.jpg",
                  "alternativeText": "",
                  "caption": "",
                  "width": 825,
                  "height": 361,
                  "formats": {
                    "small": {
                      "ext": ".jpg",
                      "url": "https://strapi-jkt-digi46.s3.ap-southeast-3.amazonaws.com/small_bni_credit_card_banner_fitur_mbanking_small_b8d822ed1c.jpg",
                      "hash": "small_bni_credit_card_banner_fitur_mbanking_small_b8d822ed1c",
                      "mime": "image/jpeg",
                      "name": "small_bni-credit-card-banner-fitur-mbanking-small.jpg",
                      "path": null,
                      "size": 18.45,
                      "width": 500,
                      "height": 219
                    },
                    "medium": {
                      "ext": ".jpg",
                      "url": "https://strapi-jkt-digi46.s3.ap-southeast-3.amazonaws.com/medium_bni_credit_card_banner_fitur_mbanking_small_b8d822ed1c.jpg",
                      "hash": "medium_bni_credit_card_banner_fitur_mbanking_small_b8d822ed1c",
                      "mime": "image/jpeg",
                      "name": "medium_bni-credit-card-banner-fitur-mbanking-small.jpg",
                      "path": null,
                      "size": 33.43,
                      "width": 750,
                      "height": 328
                    },
                    "thumbnail": {
                      "ext": ".jpg",
                      "url": "https://strapi-jkt-digi46.s3.ap-southeast-3.amazonaws.com/thumbnail_bni_credit_card_banner_fitur_mbanking_small_b8d822ed1c.jpg",
                      "hash": "thumbnail_bni_credit_card_banner_fitur_mbanking_small_b8d822ed1c",
                      "mime": "image/jpeg",
                      "name": "thumbnail_bni-credit-card-banner-fitur-mbanking-small.jpg",
                      "path": null,
                      "size": 6.15,
                      "width": 245,
                      "height": 107
                    }
                  },
                  "hash": "bni_credit_card_banner_fitur_mbanking_small_b8d822ed1c",
                  "ext": ".jpg",
                  "mime": "image/jpeg",
                  "size": 39.9,
                  "url": "https://strapi-jkt-digi46.s3.ap-southeast-3.amazonaws.com/bni_credit_card_banner_fitur_mbanking_small_b8d822ed1c.jpg",
                  "previewUrl": null,
                  "provider": "aws-s3",
                  "provider_metadata": null,
                  "created_at": "2021-04-02T19:17:54.427Z",
                  "updated_at": "2021-04-02T19:17:54.453Z"
                }
              },
              {
                "id": 7,
                "Title": null,
                "published_at": "2021-04-15T05:52:34.665Z",
                "created_at": "2021-04-14T03:33:57.208Z",
                "updated_at": "2021-07-31T14:49:08.295Z",
                "name_promo": null,
                "desc_promo": null,
                "nama": "BNI QRIS",
                "desc": "Nikmati bayar tunai tanpa kartu di bni mobile banking, anda bisa scan qris pada semua merchant yang ada di merchant yang berpartisipasi pada MCC Groceries, Pharmacies, dan Minimarket.\nInfo lebih lanjut hubungi BNI Call 1500046",
                "latitude": "-6.3468207686453",
                "longitude": "106.56324387883",
                "alt": "17",
                "createdAt": "2021-04-14",
                "lokasi": "Parung Panjang",
                "count": 15,
                "img": {
                  "id": 4,
                  "name": "bni-banner-qris-mudah-2020-small.jpg",
                  "alternativeText": "",
                  "caption": "",
                  "width": 825,
                  "height": 298,
                  "formats": {
                    "small": {
                      "ext": ".jpg",
                      "url": "https://strapi-jkt-digi46.s3.ap-southeast-3.amazonaws.com/small_bni_banner_qris_mudah_2020_small_8f0e73341a.jpg",
                      "hash": "small_bni_banner_qris_mudah_2020_small_8f0e73341a",
                      "mime": "image/jpeg",
                      "name": "small_bni-banner-qris-mudah-2020-small.jpg",
                      "path": null,
                      "size": 21.83,
                      "width": 500,
                      "height": 181
                    },
                    "medium": {
                      "ext": ".jpg",
                      "url": "https://strapi-jkt-digi46.s3.ap-southeast-3.amazonaws.com/medium_bni_banner_qris_mudah_2020_small_8f0e73341a.jpg",
                      "hash": "medium_bni_banner_qris_mudah_2020_small_8f0e73341a",
                      "mime": "image/jpeg",
                      "name": "medium_bni-banner-qris-mudah-2020-small.jpg",
                      "path": null,
                      "size": 39.78,
                      "width": 750,
                      "height": 271
                    },
                    "thumbnail": {
                      "ext": ".jpg",
                      "url": "https://strapi-jkt-digi46.s3.ap-southeast-3.amazonaws.com/thumbnail_bni_banner_qris_mudah_2020_small_8f0e73341a.jpg",
                      "hash": "thumbnail_bni_banner_qris_mudah_2020_small_8f0e73341a",
                      "mime": "image/jpeg",
                      "name": "thumbnail_bni-banner-qris-mudah-2020-small.jpg",
                      "path": null,
                      "size": 6.9,
                      "width": 245,
                      "height": 88
                    }
                  },
                  "hash": "bni_banner_qris_mudah_2020_small_8f0e73341a",
                  "ext": ".jpg",
                  "mime": "image/jpeg",
                  "size": 46.39,
                  "url": "https://strapi-jkt-digi46.s3.ap-southeast-3.amazonaws.com/bni_banner_qris_mudah_2020_small_8f0e73341a.jpg",
                  "previewUrl": null,
                  "provider": "aws-s3",
                  "provider_metadata": null,
                  "created_at": "2021-04-02T19:17:54.525Z",
                  "updated_at": "2021-04-02T19:17:54.537Z"
                }
              },
              {
                "id": 9,
                "Title": null,
                "published_at": "2021-04-15T05:52:40.799Z",
                "created_at": "2021-04-14T03:36:22.199Z",
                "updated_at": "2021-08-02T07:13:33.269Z",
                "name_promo": null,
                "desc_promo": null,
                "nama": "BNI SMS Notifikasi",
                "desc": "Aktifkan segera sms notifikasi untuk menambah keamanan transaksimu.\nInfo lebih lanjut hubungi BNI Call 1500046",
                "latitude": "-6.369428",
                "longitude": "106.894329",
                "alt": "20",
                "createdAt": "2021-04-12",
                "lokasi": "Cibubur",
                "count": 2,
                "img": {
                  "id": 2,
                  "name": "bni-sms-notifikasi-2020-small.jpg",
                  "alternativeText": "",
                  "caption": "",
                  "width": 825,
                  "height": 298,
                  "formats": {
                    "small": {
                      "ext": ".jpg",
                      "url": "https://strapi-jkt-digi46.s3.ap-southeast-3.amazonaws.com/small_bni_sms_notifikasi_2020_small_3f0447da34.jpg",
                      "hash": "small_bni_sms_notifikasi_2020_small_3f0447da34",
                      "mime": "image/jpeg",
                      "name": "small_bni-sms-notifikasi-2020-small.jpg",
                      "path": null,
                      "size": 11.95,
                      "width": 500,
                      "height": 181
                    },
                    "medium": {
                      "ext": ".jpg",
                      "url": "https://strapi-jkt-digi46.s3.ap-southeast-3.amazonaws.com/medium_bni_sms_notifikasi_2020_small_3f0447da34.jpg",
                      "hash": "medium_bni_sms_notifikasi_2020_small_3f0447da34",
                      "mime": "image/jpeg",
                      "name": "medium_bni-sms-notifikasi-2020-small.jpg",
                      "path": null,
                      "size": 21.42,
                      "width": 750,
                      "height": 271
                    },
                    "thumbnail": {
                      "ext": ".jpg",
                      "url": "https://strapi-jkt-digi46.s3.ap-southeast-3.amazonaws.com/thumbnail_bni_sms_notifikasi_2020_small_3f0447da34.jpg",
                      "hash": "thumbnail_bni_sms_notifikasi_2020_small_3f0447da34",
                      "mime": "image/jpeg",
                      "name": "thumbnail_bni-sms-notifikasi-2020-small.jpg",
                      "path": null,
                      "size": 4.12,
                      "width": 245,
                      "height": 88
                    }
                  },
                  "hash": "bni_sms_notifikasi_2020_small_3f0447da34",
                  "ext": ".jpg",
                  "mime": "image/jpeg",
                  "size": 25.46,
                  "url": "https://strapi-jkt-digi46.s3.ap-southeast-3.amazonaws.com/bni_sms_notifikasi_2020_small_3f0447da34.jpg",
                  "previewUrl": null,
                  "provider": "aws-s3",
                  "provider_metadata": null,
                  "created_at": "2021-04-02T19:17:54.380Z",
                  "updated_at": "2021-04-02T19:17:54.421Z"
                }
              },
              {
                "id": 6,
                "Title": null,
                "published_at": "2021-04-14T03:32:08.101Z",
                "created_at": "2021-04-14T03:32:03.183Z",
                "updated_at": "2021-08-02T07:24:52.582Z",
                "name_promo": null,
                "desc_promo": null,
                "nama": "BNI Taplus Poin Plus",
                "desc": "Tukarkan BNI Rewards Point dengan voucher belanja TipTop minimal 10.000 BNI Rewards Point atau senilai Rp. 50.000,-\nPenukaran BNI Rewards Point dilakukan di mesin EDC yang ada di Customer Service TipTop. Berlaku setiap hari termasuk hari libur nasional.\nInfo lebih lanjut hubungi BNI Call 1500046",
                "latitude": "-6.375195",
                "longitude": "106.901988",
                "alt": "20",
                "createdAt": "2021-04-14",
                "lokasi": "Cibubur",
                "count": 20,
                "img": {
                  "id": 5,
                  "name": "promo-bni-point-plus-januari-2021-small.jpg",
                  "alternativeText": "",
                  "caption": "",
                  "width": 825,
                  "height": 298,
                  "formats": {
                    "small": {
                      "ext": ".jpg",
                      "url": "https://strapi-jkt-digi46.s3.ap-southeast-3.amazonaws.com/small_promo_bni_point_plus_januari_2021_small_1fb82bf7c6.jpg",
                      "hash": "small_promo_bni_point_plus_januari_2021_small_1fb82bf7c6",
                      "mime": "image/jpeg",
                      "name": "small_promo-bni-point-plus-januari-2021-small.jpg",
                      "path": null,
                      "size": 15.63,
                      "width": 500,
                      "height": 181
                    },
                    "medium": {
                      "ext": ".jpg",
                      "url": "https://strapi-jkt-digi46.s3.ap-southeast-3.amazonaws.com/medium_promo_bni_point_plus_januari_2021_small_1fb82bf7c6.jpg",
                      "hash": "medium_promo_bni_point_plus_januari_2021_small_1fb82bf7c6",
                      "mime": "image/jpeg",
                      "name": "medium_promo-bni-point-plus-januari-2021-small.jpg",
                      "path": null,
                      "size": 27.21,
                      "width": 750,
                      "height": 271
                    },
                    "thumbnail": {
                      "ext": ".jpg",
                      "url": "https://strapi-jkt-digi46.s3.ap-southeast-3.amazonaws.com/thumbnail_promo_bni_point_plus_januari_2021_small_1fb82bf7c6.jpg",
                      "hash": "thumbnail_promo_bni_point_plus_januari_2021_small_1fb82bf7c6",
                      "mime": "image/jpeg",
                      "name": "thumbnail_promo-bni-point-plus-januari-2021-small.jpg",
                      "path": null,
                      "size": 5.11,
                      "width": 245,
                      "height": 88
                    }
                  },
                  "hash": "promo_bni_point_plus_januari_2021_small_1fb82bf7c6",
                  "ext": ".jpg",
                  "mime": "image/jpeg",
                  "size": 31.53,
                  "url": "https://strapi-jkt-digi46.s3.ap-southeast-3.amazonaws.com/promo_bni_point_plus_januari_2021_small_1fb82bf7c6.jpg",
                  "previewUrl": null,
                  "provider": "aws-s3",
                  "provider_metadata": null,
                  "created_at": "2021-04-02T19:17:54.580Z",
                  "updated_at": "2021-04-02T19:36:16.619Z"
                }
              },
              {
                "id": 11,
                "Title": null,
                "published_at": "2021-05-25T06:32:21.868Z",
                "created_at": "2021-05-25T06:32:19.527Z",
                "updated_at": "2021-05-25T06:32:21.893Z",
                "name_promo": null,
                "desc_promo": null,
                "nama": "BNI Kartu Kredit",
                "desc": "Transaksi lebih leluasa karena bayarnya lebih ringan",
                "latitude": "-6.3468207686453",
                "longitude": "106.56324387883",
                "alt": "17",
                "createdAt": "2021-05-25",
                "lokasi": "Pejompongan",
                "count": 100,
                "img": {
                  "id": 9,
                  "name": "Cicilan 0% Top Merchant.jpg",
                  "alternativeText": "",
                  "caption": "",
                  "width": 1237,
                  "height": 542,
                  "formats": {
                    "large": {
                      "ext": ".jpg",
                      "url": "https://strapi-jkt-digi46.s3.ap-southeast-3.amazonaws.com/large_Cicilan_0_Top_Merchant_7b7948916b.jpg",
                      "hash": "large_Cicilan_0_Top_Merchant_7b7948916b",
                      "mime": "image/jpeg",
                      "name": "large_Cicilan 0% Top Merchant.jpg",
                      "path": null,
                      "size": 48.49,
                      "width": 1000,
                      "height": 438
                    },
                    "small": {
                      "ext": ".jpg",
                      "url": "https://strapi-jkt-digi46.s3.ap-southeast-3.amazonaws.com/small_Cicilan_0_Top_Merchant_7b7948916b.jpg",
                      "hash": "small_Cicilan_0_Top_Merchant_7b7948916b",
                      "mime": "image/jpeg",
                      "name": "small_Cicilan 0% Top Merchant.jpg",
                      "path": null,
                      "size": 17.34,
                      "width": 500,
                      "height": 219
                    },
                    "medium": {
                      "ext": ".jpg",
                      "url": "https://strapi-jkt-digi46.s3.ap-southeast-3.amazonaws.com/medium_Cicilan_0_Top_Merchant_7b7948916b.jpg",
                      "hash": "medium_Cicilan_0_Top_Merchant_7b7948916b",
                      "mime": "image/jpeg",
                      "name": "medium_Cicilan 0% Top Merchant.jpg",
                      "path": null,
                      "size": 32.24,
                      "width": 750,
                      "height": 329
                    },
                    "thumbnail": {
                      "ext": ".jpg",
                      "url": "https://strapi-jkt-digi46.s3.ap-southeast-3.amazonaws.com/thumbnail_Cicilan_0_Top_Merchant_7b7948916b.jpg",
                      "hash": "thumbnail_Cicilan_0_Top_Merchant_7b7948916b",
                      "mime": "image/jpeg",
                      "name": "thumbnail_Cicilan 0% Top Merchant.jpg",
                      "path": null,
                      "size": 5.93,
                      "width": 245,
                      "height": 107
                    }
                  },
                  "hash": "Cicilan_0_Top_Merchant_7b7948916b",
                  "ext": ".jpg",
                  "mime": "image/jpeg",
                  "size": 69.03,
                  "url": "https://strapi-jkt-digi46.s3.ap-southeast-3.amazonaws.com/Cicilan_0_Top_Merchant_7b7948916b.jpg",
                  "previewUrl": null,
                  "provider": "aws-s3",
                  "provider_metadata": null,
                  "created_at": "2021-04-22T03:25:27.946Z",
                  "updated_at": "2021-04-22T03:25:27.962Z"
                }
              },
              {
                "id": 14,
                "Title": null,
                "published_at": "2021-05-25T06:34:34.962Z",
                "created_at": "2021-05-25T06:34:33.177Z",
                "updated_at": "2021-05-25T06:34:35.077Z",
                "name_promo": null,
                "desc_promo": null,
                "nama": "BNI Kartu Kredit",
                "desc": "Menangkan undian dengan apply kartu kredit via bni mobile banking",
                "latitude": null,
                "longitude": null,
                "alt": null,
                "createdAt": "2021-05-25",
                "lokasi": "Cibubur",
                "count": 100,
                "img": {
                  "id": 13,
                  "name": "bni-credit-card-apply-kk-via-mobile-small.jpg",
                  "alternativeText": "",
                  "caption": "",
                  "width": 825,
                  "height": 298,
                  "formats": {
                    "small": {
                      "ext": ".jpg",
                      "url": "https://strapi-jkt-digi46.s3.ap-southeast-3.amazonaws.com/small_bni_credit_card_apply_kk_via_mobile_small_e1e8aaf172.jpg",
                      "hash": "small_bni_credit_card_apply_kk_via_mobile_small_e1e8aaf172",
                      "mime": "image/jpeg",
                      "name": "small_bni-credit-card-apply-kk-via-mobile-small.jpg",
                      "path": null,
                      "size": 17.07,
                      "width": 500,
                      "height": 181
                    },
                    "medium": {
                      "ext": ".jpg",
                      "url": "https://strapi-jkt-digi46.s3.ap-southeast-3.amazonaws.com/medium_bni_credit_card_apply_kk_via_mobile_small_e1e8aaf172.jpg",
                      "hash": "medium_bni_credit_card_apply_kk_via_mobile_small_e1e8aaf172",
                      "mime": "image/jpeg",
                      "name": "medium_bni-credit-card-apply-kk-via-mobile-small.jpg",
                      "path": null,
                      "size": 30.31,
                      "width": 750,
                      "height": 271
                    },
                    "thumbnail": {
                      "ext": ".jpg",
                      "url": "https://strapi-jkt-digi46.s3.ap-southeast-3.amazonaws.com/thumbnail_bni_credit_card_apply_kk_via_mobile_small_e1e8aaf172.jpg",
                      "hash": "thumbnail_bni_credit_card_apply_kk_via_mobile_small_e1e8aaf172",
                      "mime": "image/jpeg",
                      "name": "thumbnail_bni-credit-card-apply-kk-via-mobile-small.jpg",
                      "path": null,
                      "size": 5.46,
                      "width": 245,
                      "height": 88
                    }
                  },
                  "hash": "bni_credit_card_apply_kk_via_mobile_small_e1e8aaf172",
                  "ext": ".jpg",
                  "mime": "image/jpeg",
                  "size": 36.82,
                  "url": "https://strapi-jkt-digi46.s3.ap-southeast-3.amazonaws.com/bni_credit_card_apply_kk_via_mobile_small_e1e8aaf172.jpg",
                  "previewUrl": null,
                  "provider": "aws-s3",
                  "provider_metadata": null,
                  "created_at": "2021-04-23T04:43:04.743Z",
                  "updated_at": "2021-04-23T04:43:04.755Z"
                }
              }
            ]
        """.trimIndent())

        val listPromo = ArrayList<ModelPromo>()
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            var jsonObjectImg = jsonObject.getJSONObject("img")
            jsonObjectImg = jsonObjectImg.getJSONObject("formats")
            val jsonImageSmall = jsonObjectImg.getJSONObject("small")
            val jsonImageMedium = jsonObjectImg.getJSONObject("medium")
            listPromo.add(
                ModelPromo(
                    jsonObject.getString("nama"),
                    jsonObject.getString("desc"),
                    jsonImageSmall.getString("url"),
                    jsonImageMedium.getString("url"),
                    jsonObject.getString("createdAt")
                )
            )

            _promos.value = listPromo

        }
    }
}