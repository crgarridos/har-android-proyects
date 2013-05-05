<?php
include './utils/class_MailManager.php';
$json["cantidad"] = MailManager::getTotalUnread(MailManager::GMAIL_IMAP, "misterharry.com@gmail.com", "0303456");
echo json_encode($json);
?>