//    Copyright (C) 2017 MD. Ibrahim Khan
//
//    Project Name: 
//    Author: MD. Ibrahim Khan
//    Author's Email: ib.arshad777@gmail.com
//
//    Redistribution and use in source and binary forms, with or without modification,
//    are permitted provided that the following conditions are met:
//
//    1. Redistributions of source code must retain the above copyright notice, this
//       list of conditions and the following disclaimer.
//
//    2. Redistributions in binary form must reproduce the above copyright notice, this
//       list of conditions and the following disclaimer in the documentation and/or
//       other materials provided with the distribution.
//
//    3. Neither the name of the copyright holder nor the names of the contributors may
//       be used to endorse or promote products derived from this software without
//       specific prior written permission.
//
//    THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
//    ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
//    WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
//    IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
//    INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING
//    BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
//    DATA, OR PROFITS; OR BUSINESS INTERRUPTIONS) HOWEVER CAUSED AND ON ANY THEORY OF
//    LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
//    OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
//    OF THE POSSIBILITY OF SUCH DAMAGE.

package watchlistmanager.model.data;

/**
 *
 * @author Arshad
 */
public class EntryDetailed {
    private final String table;
    private final String index;
    private String name;
    private String info;
    private String status;
    private String episodes;
    private String seen;
    private final String time_created;
    private String time_modified;
    
    public EntryDetailed(String table, String index, String name, String info, String status, String episodes, String seen, String timeC, String timeM) {
        this.table = table;
        this.index = index;
        this.name = name;
        this.info = info;
        this.status = status;
        this.episodes = episodes;
        this.seen = seen;
        this.time_created = timeC;
        this.time_modified = timeM;
    }
    
    public void set(String name, String info, String status, String episodes, String seen) {
        this.name = name;
        this.info = info;
        this.status = status;
        this.episodes = episodes;
        this.seen = seen;
        time_modified = String.valueOf(System.currentTimeMillis());
    }

    public String getTable() {
        return table;
    }

    public String getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public String getInfo() {
        return info;
    }

    public String getStatus() {
        return status;
    }

    public String getEpisodes() {
        return episodes;
    }

    public String getSeen() {
        return seen;
    }
    public String getTime_created() {
        return time_created;
    }

    public String getTime_modified() {
        return time_modified;
    }
    
    @Override
    public String toString() {
        return this.name;
    }
}
