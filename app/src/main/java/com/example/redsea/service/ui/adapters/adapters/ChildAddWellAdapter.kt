package com.example.redsea.service.ui.adapters.adapters

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.redsea.R
import com.example.redsea.network.PostData.Publish
import com.example.redsea.network.PostData.Text
import com.example.redsea.network.PostData.WellData
import com.example.redsea.network.Response.WellOptions.StructureDescription
import org.json.JSONObject

class ChildAddWellAdapter(val structureDescription: List<StructureDescription>) :
    RecyclerView.Adapter<ChildAddWellAdapter.BaseViewHolder>() {

    abstract class BaseViewHolder(viewItem: View) : RecyclerView.ViewHolder(viewItem)

    val inputList = Text(
        MutableList(structureDescription.size) { "" },
        MutableList(structureDescription.size) { "" },
        MutableList(structureDescription.size) { "" },
        MutableList(structureDescription.size) { "" },
        MutableList(structureDescription.size) { "" },
        MutableList(structureDescription.size) { "" }
    )
    var input: Publish = Publish("", "", "", mutableListOf())
    var test = input.well_data

    companion object {
        const val VIEW_NORMAL = 1
        const val VIEW_MULTITEXT = 2
        const val VIEW_LIST = 3
    }


    inner class ChildViewHolder(itemView: View) : BaseViewHolder(itemView) {
        val textView = itemView.findViewById<TextView>(R.id.wellPropertiesTV)
        val inputText = itemView.findViewById<EditText>(R.id.addWellInput)
    }

    inner class MultiTextViewHolder(viewItem: View) : BaseViewHolder(viewItem) {

        val textViewMultiText = viewItem.findViewById<TextView>(R.id.childMultiTextTV)
        val piMultiInput = viewItem.findViewById<EditText>(R.id.piMultiTextInput)
        val pdMultiInput = viewItem.findViewById<EditText>(R.id.pdMultiTextInput)
        val tiMultiInput = viewItem.findViewById<EditText>(R.id.tiMultiTextInput)
        val tmMultiInput = viewItem.findViewById<EditText>(R.id.tmMultiTextInput)
        val ctMultiInput = viewItem.findViewById<EditText>(R.id.ctMultiTextInput)

        fun getMultiTextData(structureId: Int): WellData {
            // Choose one of the following based on your requirement

            // Option 1: If you want to concatenate the values into a single string
            val piText = piMultiInput.text ?: "defaultPiValue"
            val pdText = pdMultiInput.text ?: "defaultPdValue"
            val tiText = tiMultiInput.text ?: "defaultTiValue"
            val tmText = tmMultiInput.text ?: "defaultTmValue"
            val ctText = ctMultiInput.text ?: "defaultCtValue"

            val concatenatedValue = "{Pi:$piText, Pd:$pdText, Ti:$tiText, Tm:$tmText, Ct:$ctText}"
            Log.d("TESTTM", concatenatedValue)

           // return WellData(structure_description_id = structureId, data = concatenatedValue)
            var test = "{\r\n            \"Pi\":\"${piMultiInput.text}\",\r\n            \"Pd\":\"${pdMultiInput.text}\",\r\n            \"Ti\":\"${tiMultiInput.text}\",\r\n            \"Tm\":\"${tmMultiInput.text}\",\r\n            \"Ct\":\"${ctMultiInput.text}\"\r\n        }\r\n    }\r\n    ]\r\n}\r\n"
             var test2 = "{\r\n" +
                    "    \"Pi\":\"${piText}\",\r\n" +
                    "    \"Pd\":\"${pdText}\",\r\n" +
                    "    \"Ti\":\"${tiText}\",\r\n" +
                    "    \"Tm\":\"${tmText}\",\r\n" +
                    "    \"Ct\":\"${ctText}\"\r\n" +
                    "}"

            val jsonData = JSONObject().apply {
                put("Pi", piMultiInput.text.toString())
                put("Pd", pdMultiInput.text.toString())
                put("Ti", tiMultiInput.text.toString())
                put("Tm", tmMultiInput.text.toString())
                put("Ct", ctMultiInput.text.toString())
            }

            //val jsonString = JSONObject(jsonData)

            return WellData(structure_description_id = structureId, data = test2)

        }
        fun updateInputObject(structureId: Int) {
            val wellData = getMultiTextData(structureId)

            // Check if the structure_description_id already exists in well_data
            val existingWellData =
                input.well_data.find { it.structure_description_id == structureId }

            if (existingWellData != null) {
                // If it exists, update the data
                existingWellData.data = wellData.data
            } else {
                // If it doesn't exist, add a new WellData entry
                input.well_data.add(wellData)
            }

            Log.d("PRINTINPUT1", input.well_data.toString())
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        input.well_data = mutableListOf()

        when (viewType) {
            VIEW_MULTITEXT -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_child_multitext, parent, false)
                return MultiTextViewHolder(view)

            }

            VIEW_NORMAL -> {
                val view =
                    LayoutInflater.from(parent.context).inflate(R.layout.item_child, parent, false)
                return ChildViewHolder(view)
            }
        }
        return super.createViewHolder(parent, viewType)
    }

    override fun getItemCount(): Int {
        Log.d("Child Size", structureDescription.size.toString())
        return structureDescription.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        Log.d("Child Text View", structureDescription[position].input)
        var pos = position
        holder.setIsRecyclable(false)
        when (holder) {
            is MultiTextViewHolder -> {
                holder.textViewMultiText.text = structureDescription[position].input
                try {
                    holder.ctMultiInput.addTextChangedListener(object : TextWatcher {
                        override fun beforeTextChanged(
                            s: CharSequence?,
                            start: Int,
                            count: Int,
                            after: Int
                        ) {
                        }

                        override fun onTextChanged(
                            s: CharSequence?,
                            start: Int,
                            before: Int,
                            count: Int
                        ) {
                        }

                        override fun afterTextChanged(s: Editable?) {
                            try {
                                inputList.ctMultiText[pos] = s.toString()
                                Log.d("TEXT HELLO CT", inputList.ctMultiText[pos])
                                val structureId = structureDescription[pos].id
                               /* val newData = s.toString()

                                // Check if the structure_description_id already exists in well_data
                                val existingWellData = input.well_data.find { it.structure_description_id == structureId }

                                if (existingWellData != null) {
                                    // If it exists, update the data
                                    existingWellData.data = newData
                                } else {
                                    // If it doesn't exist, add a new WellData entry
                                    input.well_data.add(WellData(structure_description_id = structureId, data = newData))
                                }*/
                                holder.updateInputObject(structureId)

                                Log.d("PRINTINPUT1", input.well_data.toString())
                            } catch (e: Exception) {
                                Log.d("TEXT HELLO ERRORCT", e.message.toString())
                            }
                        }
                    })
                    holder.pdMultiInput.addTextChangedListener(object : TextWatcher {
                        override fun beforeTextChanged(
                            s: CharSequence?,
                            start: Int,
                            count: Int,
                            after: Int
                        ) {
                        }

                        override fun onTextChanged(
                            s: CharSequence?,
                            start: Int,
                            before: Int,
                            count: Int
                        ) {
                        }

                        override fun afterTextChanged(s: Editable?) {
                            try {
                                inputList.pdMultiText[pos] = s.toString()
                                Log.d("TEXTHELLOPD", inputList.pdMultiText[pos])
                                val structureId = structureDescription[pos].id
                                /*val newData = s.toString()

                                // Check if the structure_description_id already exists in well_data
                                val existingWellData = input.well_data.find { it.structure_description_id == structureId }

                                if (existingWellData != null) {
                                    // If it exists, update the data
                                    existingWellData.data = newData
                                } else {
                                    // If it doesn't exist, add a new WellData entry
                                    input.well_data.add(WellData(structure_description_id = structureId, data = newData))
                                }*/
                                holder.updateInputObject(structureId)

                                Log.d("PRINTINPUT1", input.well_data.toString())
                            } catch (e: Exception) {
                                Log.d("TEXT HELLO ERRORPD", e.message.toString())
                            }
                        }
                    })
                    holder.piMultiInput.addTextChangedListener(object : TextWatcher {
                        override fun beforeTextChanged(
                            s: CharSequence?,
                            start: Int,
                            count: Int,
                            after: Int
                        ) {
                        }

                        override fun onTextChanged(
                            s: CharSequence?,
                            start: Int,
                            before: Int,
                            count: Int
                        ) {
                        }

                        override fun afterTextChanged(s: Editable?) {
                            try {
                                inputList.piMultiText[pos] = s.toString()
                                Log.d("TEXTHELLOPI", inputList.piMultiText[pos].toString())
                                Log.d("TEXTHELLOPI", inputList.piMultiText.size.toString())
                                val structureId = structureDescription[pos].id
                                /*val newData = s.toString()

                                // Check if the structure_description_id already exists in well_data
                                val existingWellData = input.well_data.find { it.structure_description_id == structureId }

                                if (existingWellData != null) {
                                    // If it exists, update the data
                                    existingWellData.data = newData
                                } else {
                                    // If it doesn't exist, add a new WellData entry
                                    input.well_data.add(WellData(structure_description_id = structureId, data = newData))
                                }*/
                                holder.updateInputObject(structureId)
                                Log.d("PRINTINPUT1", input.well_data.toString())
                            } catch (e: Exception) {
                                Log.d("TEXT HELLO ERRORPI", e.message.toString())
                            }
                        }
                    })
                    holder.tiMultiInput.addTextChangedListener(object : TextWatcher {
                        override fun beforeTextChanged(
                            s: CharSequence?,
                            start: Int,
                            count: Int,
                            after: Int
                        ) {
                        }

                        override fun onTextChanged(
                            s: CharSequence?,
                            start: Int,
                            before: Int,
                            count: Int
                        ) {
                        }

                        override fun afterTextChanged(s: Editable?) {
                            try {
                                inputList.tiMultiText[pos] = s.toString()
                                Log.d("TEXT HELLOTI", inputList.tiMultiText[pos].toString())
                                val structureId = structureDescription[pos].id
                                /*val newData = s.toString()

                                // Check if the structure_description_id already exists in well_data
                                val existingWellData = input.well_data.find { it.structure_description_id == structureId }

                                if (existingWellData != null) {
                                    // If it exists, update the data
                                    existingWellData.data = newData
                                } else {
                                    // If it doesn't exist, add a new WellData entry
                                    input.well_data.add(WellData(structure_description_id = structureId, data = newData))
                                }*/
                                holder.updateInputObject(structureId)
                                Log.d("PRINTINPUT1", input.well_data.toString())
                            } catch (e: Exception) {
                                Log.d("TEXT HELLO ERRORTI", e.message.toString())
                            }
                        }


                    })
                    holder.tmMultiInput.addTextChangedListener(object : TextWatcher {
                        override fun beforeTextChanged(
                            s: CharSequence?,
                            start: Int,
                            count: Int,
                            after: Int
                        ) {
                        }

                        override fun onTextChanged(
                            s: CharSequence?,
                            start: Int,
                            before: Int,
                            count: Int
                        ) {
                        }

                        override fun afterTextChanged(s: Editable?) {
                            try {
                                inputList.tmMultiText[pos] = s.toString()
                                Log.d("TEXT HELLOTM", inputList.tmMultiText[pos].toString())
                                val structureId = structureDescription[pos].id
                                /*val newData = s.toString()

                                // Check if the structure_description_id already exists in well_data
                                val existingWellData = input.well_data.find { it.structure_description_id == structureId }

                                if (existingWellData != null) {
                                    // If it exists, update the data
                                    existingWellData.data = newData
                                } else {
                                    // If it doesn't exist, add a new WellData entry
                                    input.well_data.add(WellData(structure_description_id = structureId, data = newData))
                                }*/
                                holder.updateInputObject(structureId)
                                Log.d("PRINTINPUT1", input.well_data.toString())
                            } catch (e: Exception) {
                                Log.d("TEXT HELLO ERRORTM", e.message.toString())
                            }
                        }


                    })
                } catch (e: Exception) {
                    Log.d("TEXT HELLO", e.message.toString())
                }


            }

            is ChildViewHolder -> {
                holder.setIsRecyclable(false)
                holder.textView.text = structureDescription[position].input
                try {

                    holder.inputText.addTextChangedListener(object : TextWatcher {
                        override fun beforeTextChanged(
                            s: CharSequence?,
                            start: Int,
                            count: Int,
                            after: Int
                        ) {
                            val structureId = structureDescription[pos].id

                            // Check if the structure_description_id already exists in well_data
                            val existingWellData = input.well_data.find { it.structure_description_id == structureId }

                            if (existingWellData == null) {
                                // If it doesn't exist, add a new WellData entry
                                input.well_data.add(WellData(structure_description_id = structureId, data = ""))
                            }
                        }

                        override fun onTextChanged(
                            s: CharSequence?,
                            start: Int,
                            before: Int,
                            count: Int
                        ) {
                        }

                        override fun afterTextChanged(s: Editable?) {
                            try {

                                inputList.textArray[pos] = s.toString()
                                Log.d("TEXT HELLO", inputList.textArray[pos].toString())
                                val structureId = structureDescription[pos].id
                                val newData = s.toString()

// Check if the structure_description_id already exists in well_data
                                val existingWellData = input.well_data.find { it.structure_description_id == structureId }

                                if (existingWellData != null) {
                                    // If it exists, update the data
                                    existingWellData.data = newData
                                } else {
                                    // If it doesn't exist, add a new WellData entry
                                    input.well_data.add(WellData(structure_description_id = structureId, data = newData))
                                }

                                Log.d("PRINTINPUT1", input.well_data.toString())

                               // input.well_data.add(WellData(structure_description_id = structureDescription[pos].id, data = s.toString()))
                                Log.d("PRINTINPUT1", input.well_data.toString())
                            } catch (e: Exception) {
                                Log.d("TEXT HELLO ERROR", e.message.toString())
                            }
                        }


                    })
                } catch (e: Exception) {
                    Log.d("TEXT HELLO", e.message.toString())
                }

            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (structureDescription[position].type == "MultiText") {
            return VIEW_MULTITEXT
        } else return VIEW_NORMAL
    }

    fun enteredList() : Publish
    {
        return input
    }


}