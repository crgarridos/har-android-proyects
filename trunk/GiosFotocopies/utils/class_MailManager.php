<?php

class MailManager {
    
    const YAHOO_IMAP = "{in.izymail.com:143/imap}";
    const YAHOO_POP3 = "{in.izymail.com:110/pop3}";
    
    const GMAIL_IMAP = "{imap.gmail.com:993/imap/ssl/novalidate-cert}";
    const GMAIL_POP3 = "{pop.gmail.com:995/pop3/ssl/novalidate-cert}";
    
    const HOTMAIL_POP3 = "{pop3.live.com:995/pop3}";
    
    public static function printStatus($host, $user, $pass) {
        $mbox = imap_open ($host, $user, $pass);//{".$host.":143}INBOX
        $estado = imap_status($mbox,$host, SA_ALL);
        if ($estado) {
          echo "Mensajes:    " . $estado->messages    . "<br />\n";
          echo "Recientes:   " . $estado->recent      . "<br />\n";
          echo "No vistos:   " . $estado->unseen      . "<br />\n";
          echo "SiguienteUID:" . $estado->uidnext     . "<br />\n";
          echo "ValidezUID:  " . $estado->uidvalidity . "<br />\n";
        } else {
          echo "imap_status failed: " . imap_last_error() . "\n";
        }
        imap_close($mbox);
    }
    
    public static function getTotalUnread($host, $user, $pass) {
        $mbox = imap_open ($host, $user, $pass);//{".$host.":143}INBOX
        $estado = imap_status($mbox,$host, SA_UNSEEN);
        if ($estado) return $estado->unseen;
        else echo "imap_status failed: " . imap_last_error() . "\n";
        imap_close($mbox);
    }
}

?>
