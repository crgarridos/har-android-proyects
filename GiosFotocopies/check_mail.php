<?php
include './utils/class_MailManager.php';
$json["cantidad"] = MailManager::getTotalUnread(MailManager::GMAIL_IMAP, "crgarrido.s@gmail.com", "password");
echo json_encode($json);
?>