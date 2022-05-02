package com.rouming.cinema_for_you

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class CloseAppDialog: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.closeDialog_title)
            .setMessage(R.string.closeDialog_qiestion)
            .setNegativeButton(android.R.string.cancel) { dialog, _ -> dialog.cancel() }
            .setPositiveButton(android.R.string.ok) { _, _ -> activity?.finish()}
            .create()
    }
}
